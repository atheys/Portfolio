import pygame
from pygame import gfxdraw
import random
from Planet import Planet
from Rocket import Rocket
from Explosion import Explosion
from General import to_screen, scale_pos, check_collision, to_ints, get_distance_between
from Viewmanager import Viewmanager

class Universe():
    def __init__(self, zoom_factor, zoom_speed, max_zoom, min_zoom, resolution,assignment):
        print "And God said, 'Let there be light,' and there was light. A new universe was created"
        self.stars = self.generate_starfield(8000, resolution[0]*max_zoom*2, resolution[1]*max_zoom*2)
        self.rockets = []
        self.explosions = []
        self.zoom_factor = zoom_factor
        self.zoom_speed = zoom_speed
        self.min_zoom_factor = min_zoom
        self.max_zoom_factor = max_zoom
        self.planet = "Not initialized"
        self.view_manager = Viewmanager()
        self.view_manager.add_view((10,10), (300, 200))
        self.init_view = False
        self.rocket_shot = False
        self.reset_view = False
        self.assignment = assignment
        self.level=int(1)
        self.fuel_used = 0.0
        self.can_shoot = False
        self.fuel_max = 0.0

        if assignment=="RUSSIAN":
            self.assignment="Destroy All Russian Satellites"
        if assignment=="AMERICAN":
            self.assignment="Destroy All American Satellites"
        if assignment=="EUROPEAN":
            self.assignment="Destroy All European Satellites"
        if assignment=="LANDER":
            self.assignment="Destroy the Lander(s)"
        if assignment=="ROCK":
            self.assignment="Destroy All Space Rocks"
        if assignment=="ALIEN":
            self.assignment="Destroy All Alien Spacecrafts"

    def get_zoom_factor(self):
        return self.zoom_factor

    def create_planet(self, name, mass, radius, rot_speed, sprite):
        self.planet = Planet(self, name, mass, radius, rot_speed, (0,0), sprite)

    def create_launcher(self, *l):
        self.planet.create_launcher(*l)

    def create_satellite(self, distance, direction, angle, nationality, name):
        self.planet.create_satellite(distance, direction, angle, nationality, name)

    def get_planet(self):
        return self.planet

    def get_can_shoot(self):
        return self.can_shoot

    def set_can_shoot(self, b):
        self.can_shoot = b

    def change_level(self,i):
        self.level=i

    def add_fuel(self, df):
        self.fuel_used+=df

    def get_fuel_used(self):
        return self.fuel_used

    def get_fuel_max(self):
        return self.fuel_max

    def change_fuel_max(self,a):
        self.fuel_max=a

    def get_all_satellites(self):
        return self.planet.get_satellites()

    def get_satellites(self):
        if self.assignment=="Destroy All Russian Satellites":
            return self.planet.get_russian_satellites()
        if self.assignment=="Destroy All American Satellites":
            return self.planet.get_american_satellites()
        if self.assignment=="Destroy All European Satellites" or self.assignment=="Destroy the Lander(s)":
            return self.planet.get_european_satellites()
        if self.assignment=="Destroy All Space Rocks":
            return self.planet.get_rock_satellites()
        if self.assignment=="Destroy All Alien Spacecrafts":
            return self.planet.get_alien_satellites()

    def get_all_satellites(self):
        return self.planet.get_satellites()

    def get_rockets(self):
        return self.rockets

    def get_launcher(self):
        return self.planet.get_launcher()

    def get_assignment(self):
        return self.assignment

    def launch_rocket(self, pos, dir, angle, initial_speed, rocket_fuel, spr1, spr2):
        self.rockets.append(Rocket(self, pos, dir, angle, initial_speed, 1e3, 200e3, rocket_fuel, spr1, spr2))
        self.rocket_shot = True

    def get_gravity(self, pos):
        return self.planet.get_gravity(pos)

    def check_collisions(self):
        for r in self.rockets:
            if check_collision(self.planet.get_pos(), r.get_pos(), self.planet.get_radius()-10):
                self.destruct_rocket(r, r)
                break
            for s in self.planet.get_satellites():
                if check_collision(r.get_pos(), s.get_pos(), 20):
                    self.destruct_rocket(r, s)
                    self.planet.remove_satellite(s)
                    break

    def destruct_rocket(self, r, object):
        self.reset_view = True
        r.explode()
        self.reset_view = True
        self.explosions.append(Explosion(r.get_pos(), 0.04, object.get_nationality()))
        self.rockets.remove(r)
        self.planet.load_rocket()

    def draw(self, screen):
        if not self.init_view:
            self.view_manager.set_target(0, self.planet.get_launcher(), screen)
            self.init_view = True

        if self.rocket_shot:
            self.view_manager.set_target(0, self.rockets[-1], screen)
            self.rocket_shot = False

        if self.reset_view:
            self.view_manager.set_target(0, self.get_launcher(), screen)
            self.reset_view = False

        if pygame.mouse.get_pressed()[2]:
            for satellite in self.planet.get_satellites():
                s_pos = to_ints(to_screen(scale_pos(satellite.get_pos(), self.zoom_factor)))
                if get_distance_between(s_pos, pygame.mouse.get_pos()) < 30*self.zoom_factor:
                    self.view_manager.set_target(0, satellite, screen)
            for rocket in self.planet.get_rockets():
                rocket_pos = to_ints(to_screen(scale_pos(rocket.get_pos(), self.zoom_factor)))
                if get_distance_between(rocket_pos, pygame.mouse.get_pos()) < 30*self.zoom_factor:
                    self.view_manager.set_target(0, rocket, screen)
            launcher = self.get_launcher()
            launcher_pos = to_ints(to_screen(scale_pos(launcher.get_pos(), self.zoom_factor)))
            if get_distance_between(launcher_pos, pygame.mouse.get_pos()) < 30*self.zoom_factor:
                self.view_manager.set_target(0, launcher, screen)
            planet_pos = to_ints(to_screen(scale_pos(self.planet.get_pos(), self.zoom_factor)))
            if get_distance_between(planet_pos, pygame.mouse.get_pos()) < 30*self.zoom_factor:
                self.view_manager.set_target(0, self.planet, screen)

        self.draw_stars(screen, self.stars)
        self.planet.draw(screen, self.zoom_factor)
        for rocket in self.rockets:
            rocket.draw(screen, self.zoom_factor)
        for explosion in self.explosions:
            explosion.draw(screen, self.zoom_factor)

        self.view_manager.draw(screen, self.planet, self.rockets, 1.25)

    def draw_stars(self, screen, list):
        resolution = (screen.get_rect().width, screen.get_rect().height)
        i=0
        for pos in list:
            i+=1
            if(i%3)==0:
                draw_pos = to_screen(scale_pos(pos, self.zoom_factor))
                if draw_pos[0] > -10 and draw_pos[0] < resolution[0]+10 and\
                    draw_pos[1] > -10 and draw_pos[1] < resolution[1]+10:
                    pygame.gfxdraw.filled_circle(screen, int(draw_pos[0]), int(draw_pos[1]), 1, (200,0,0))
            if(i%3)==1:
                draw_pos = to_screen(scale_pos(pos, self.zoom_factor))
                if draw_pos[0] > -10 and draw_pos[0] < resolution[0]+10 and\
                    draw_pos[1] > -10 and draw_pos[1] < resolution[1]+10:
                    pygame.gfxdraw.filled_circle(screen, int(draw_pos[0]), int(draw_pos[1]), 1, (0,0,200))
            if(i%3)==2:
                draw_pos = to_screen(scale_pos(pos, self.zoom_factor))
                if draw_pos[0] > -10 and draw_pos[0] < resolution[0]+10 and\
                    draw_pos[1] > -10 and draw_pos[1] < resolution[1]+10:
                    pygame.gfxdraw.filled_circle(screen, int(draw_pos[0]), int(draw_pos[1]), 1, (255,255,255))

    def update(self, dt):
        self.check_collisions()
        self.planet.update(dt)
        for rocket in self.rockets:
            rocket.update(dt)
        for explosion in self.explosions:
            explosion.update(dt)
            if explosion.is_done():
                self.explosions.remove(explosion)

    def pass_event(self, event):
        if event.type == pygame.MOUSEBUTTONDOWN:
            if event.button == 4:
                self.zoom_factor *= self.zoom_speed
                if self.zoom_factor > self.max_zoom_factor:
                    self.zoom_factor = self.max_zoom_factor
            if event.button == 5:
                self.zoom_factor /= self.zoom_speed
                if self.zoom_factor < self.min_zoom_factor:
                    self.zoom_factor = self.min_zoom_factor
        self.planet.pass_event(event)
        for rocket in self.rockets:
            rocket.pass_events(event)





    def generate_starfield(self, n, width, height):
        star_list = []
        for i in xrange(n):
            tempX = random.randrange(-width, width, 1)
            tempY = random.randrange(-height, height, 1)
            pos = (int(tempX), int(tempY))
            star_list.append(pos)
        return star_list