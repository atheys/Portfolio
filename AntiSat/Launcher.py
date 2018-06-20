import pygame
from General import draw, to_screen, get_angle_between, scale_pos
from math import cos, sin, radians

class Launcher():
    def __init__(self, home_planet, angle, n_rockets, rocket_fuel, sprite_pad, sprite_rocket,sprite_rocket_thrusting):
        self.home_planet = home_planet
        self.angle = angle
        self.empty = False
        self.launch_angle = 45
        self.n_rockets = n_rockets
        self.rocket_fuel = rocket_fuel
        self.nationality = "ESA"
        self.scale = 0.15
        self.offset_r = -61
        self.pos = self.home_planet.get_position()
        try:
            self.sprite_pad = pygame.image.load("Sprites/"+sprite_pad)
        except:
            print "Sprites/%s was not found" %(sprite_pad)
            self.sprite_pad = pygame.image.load("Sprites/backup.png")

        self.sprite_rocket = pygame.image.load("Sprites/"+sprite_rocket)
        self.sprite_rocket_thrusting = pygame.image.load("Sprites/"+sprite_rocket_thrusting)
        
        print "Launcher created. Now we can shoot stuff."

    def draw(self, screen, zoom_factor, offset_pos=(0,0)):
        draw_pos = (self.pos[0]-offset_pos[0], self.pos[1]-offset_pos[1])
        self.launch_angle = get_angle_between(pygame.mouse.get_pos(), to_screen(scale_pos(self.pos, zoom_factor)))
        if self.empty:
            draw(screen, self.sprite_pad, draw_pos, self.angle-90, self.scale, zoom_factor)
        else:
            draw(screen, self.sprite_pad, draw_pos, self.angle-90, self.scale, zoom_factor)
            draw(screen, self.sprite_rocket, draw_pos, -1*self.launch_angle+90, self.scale, zoom_factor)


    def update(self, dt):
        self.angle = self.home_planet.get_angle()
        self.pos = self.home_planet.get_position()
        self.temp_r = self.home_planet.get_radius()
        self.temp_x = self.pos[0] + (self.temp_r-self.offset_r) * cos(radians(self.angle))
        self.temp_y = self.pos[1] - (self.temp_r-self.offset_r) * sin(radians(self.angle))
        self.pos = (self.temp_x, self.temp_y)

    def pass_event(self, event):
        if event.type == pygame.MOUSEBUTTONDOWN:
            if event.button == 1:
                if not self.empty:
                    self.launch_rocket()

    def launch_rocket(self):
        if self.n_rockets > 0 and self.home_planet.get_home_universe().get_can_shoot():
            self.home_planet.launch_rocket(self.pos, -1*self.launch_angle+90, self.angle, self.rocket_fuel, self.sprite_rocket, self.sprite_rocket_thrusting)
            self.empty = True
            self.n_rockets -= 1

    def load_rocket(self):
        if self.n_rockets > 0:
            self.empty = False

    def get_pos(self):
        return self.pos

    def get_rockets_left(self):
        return self.n_rockets

    def get_nationality(self):
        return self.nationality

    def get_home_planet(self):
        return self.home_planet

    def get_home_universe(self):
        return self.home_planet.get_home_universe()
        
    def is_empty(self):
        if self.empty:
            return True
        else:
            return False