import pygame
from General import draw, to_screen, scale_pos, get_angle_between
from math import cos, sin, radians

class Rocket():
    def __init__(self, home_universe, pos, dir, angle, initial_speed, mass, power, fuel, sprite, sprite_thrusting):
        self.home_universe = home_universe
        self.angle = dir
        self.nationality = "NASA"
        self.power = float(power)
        self.mass = float(mass)
        self.scale = 0.15
        self.thrusting = False
        self.f = (0,0)
        self.acceleration = (0,0)
        self.v_x = -1 * initial_speed * cos(radians(angle))
        self.v_y = -1 * initial_speed * sin(radians(angle))
        self.velocity = (self.v_x, self.v_y)
        self.pos = pos
        self.volume = 0.0
        self.target_volume = 0.0
        self.exploded = False
        self.fuel = fuel
        self.sprite=sprite
        self.sprite_thrusting= sprite_thrusting
        print self.sprite
    
        self.sprite = sprite
        self.sprite_thrusting = sprite_thrusting

        try:
            self.thrust_sound = pygame.mixer.Sound("Sounds/Thrust.ogg")
        except:
            raise UserWarning, "Sound Thrust.wav was not found"

        self.thrust_sound.set_volume(self.volume)
        self.thrust_sound.play(-1)

        print "Rocket launched. Engaging full thrust now."

    def get_pos(self):
        return self.pos

    def get_home_universe(self):
        return self.home_universe

    def get_nationality(self):
        return self.nationality

    def get_fuel(self):
        return self.fuel

    def explode(self):
        self.thrust_sound.set_volume(0.0)
        self.exploded = True

    def self_destruct(self):
        self.explode()
        self.home_universe.destruct_rocket(self, self)

    def draw(self, screen, zoom_factor, offset_pos=(0,0)):
        draw_pos = (self.pos[0]-offset_pos[0], self.pos[1]-offset_pos[1])
        if not self.exploded:
            if offset_pos == (0,0):
                self.angle = -1*get_angle_between(pygame.mouse.get_pos(), to_screen(scale_pos(self.pos, zoom_factor)))+90
            if self.thrusting and self.fuel > 0:
                draw(screen, self.sprite_thrusting, draw_pos, self.angle, self.scale, zoom_factor)
            else:
                draw(screen, self.sprite, draw_pos, self.angle, self.scale, zoom_factor)

    def update(self, dt):
        self.f_x = 0.0
        self.f_y = 0.0
        self.v_x = self.velocity[0]
        self.v_y = self.velocity[1]
        self.p_x = self.pos[0]
        self.p_y = self.pos[1]

        self.acceleration = self.home_universe.get_gravity(self.pos)
        self.a_x = -self.acceleration[0]
        self.a_y = -self.acceleration[1]

        if self.thrusting and self.fuel > 0:
            self.f_x += -1*self.power*sin(radians(self.angle))
            self.f_y += -1*self.power*cos(radians(self.angle))
            self.fuel -= 1.0
            self.home_universe.add_fuel(1.0)
            self.target_volume = 1
        else:
            self.target_volume = 0.0

        self.a_x += self.f_x / self.mass
        self.a_y += self.f_y / self.mass
        self.v_x += self.a_x * dt
        self.v_y += self.a_y * dt
        self.p_x += self.v_x *dt
        self.p_y += self.v_y * dt

        self.velocity = (self.v_x, self.v_y)
        self.pos = (self.p_x, self.p_y)


        if self.volume < self.target_volume:
            self.volume += 0.02
        elif self.volume > self.target_volume:
            self.volume -= 0.04
        self.thrust_sound.set_volume(self.volume/2)

    def pass_events(self, event):
        if event.type == pygame.MOUSEBUTTONDOWN:
            if event.button == 1: self.thrusting = True
        if event.type == pygame.MOUSEBUTTONUP:
            if event.button == 1: self.thrusting = False
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_SPACE:
                self.self_destruct()
