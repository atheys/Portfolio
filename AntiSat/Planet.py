import pygame
from Satellite import Satellite
from Launcher import Launcher
from General import draw, get_distance_between
from math import radians, atan2, cos, sin


class Planet():
    def __init__(self, home_universe, name, mass, radius, rot_speed, pos, sprite):
        self.home_universe = home_universe
        self.name = name
        self.mass = mass
        self.radius = radius
        self.rot_speed = rot_speed
        self.angle = 0
        self.pos = pos
        self.satellites = []
        self.launcher = 0.0
        self.scale = 0.3
        self.gravitational_constant = 1e-4
        self.mu = self.gravitational_constant*self.mass
        try:
            self.sprite = pygame.image.load("Sprites/"+sprite)
        except:
            print "Sprites/%s was not found" %(sprite)
            self.sprite = pygame.image.load("Sprites/backup.png")

        print "Planet created. Evolution started.... civilization formed."


    def get_pos(self):
        return self.pos

    def get_radius(self):
        return self.radius

    def get_satellites(self):
        return self.satellites
    
    def get_russian_satellites(self):
        russianSatellites = []
        for t in self.satellites:    
            if t.nationality == "Roscosmos":
                russianSatellites.append(t)
        return russianSatellites        
    
    def get_american_satellites(self):
        americanSatellites = []
        for t in self.satellites:    
            if t.nationality == "NASA":
                americanSatellites.append(t)
        return americanSatellites 
        
    def get_european_satellites(self):
        europeanSatellites = []
        for t in self.satellites:    
            if t.nationality == "ESA":
                europeanSatellites.append(t)
        return europeanSatellites   

    def get_alien_satellites(self):
        naSatellites = []
        for t in self.satellites:    
            if t.nationality == "Alien":
                naSatellites.append(t)
        return naSatellites 

    def get_rock_satellites(self):
        rockSatellites = []
        for t in self.satellites:    
            if t.nationality == "Space Rock":
                rockSatellites.append(t)
        return rockSatellites 

    def get_rockets(self):
        return self.home_universe.get_rockets()

    def remove_satellite(self, satellite):
        #print "Satellite exploded"
        self.satellites.remove(satellite)

    def create_satellite(self, distance, direction, angle, nationality, name):
        self.satellites.append(Satellite(self, distance, direction, angle, nationality, name))

    def create_launcher(self, *l):
        l = list(l)
        l[0] += self.angle
        self.launcher = Launcher(self, *l)

    def get_launcher(self):
        return self.launcher

    def launch_rocket(self, pos, dir, angle, rocket_fuel, spr1, spr2):
        self.home_universe.launch_rocket(pos, dir, angle, self.radius*radians(self.rot_speed), rocket_fuel, spr1, spr2)

    def load_rocket(self):
        if self.launcher.is_empty():
            self.launcher.load_rocket()

    def get_home_universe(self):
        return self.home_universe

    def get_name(self):
        return self.name

    def get_gravity(self, pos):
        magnitude = self.mu / (get_distance_between(self.pos, pos)**2)
        angle = atan2((pos[1]-self.pos[1]),(pos[0]-self.pos[0]))
        a_x = magnitude*cos(angle)
        a_y = magnitude*sin(angle)
        return (a_x, a_y)

    def draw(self, screen, zoom_factor, offset_pos=(0,0)):
        draw_pos = (self.pos[0]-offset_pos[0], self.pos[1]-offset_pos[1])
        draw(screen, self.sprite, draw_pos, self.angle, self.scale, zoom_factor)

        for s in self.satellites:
            s.draw(screen ,zoom_factor, offset_pos)

        self.launcher.draw(screen, zoom_factor, offset_pos)





    def update(self, dt):
        self.angle -= self.rot_speed * dt

        for s in self.satellites:
            s.update(dt)

        self.launcher.update(dt)


    def pass_event(self, event):
        self.launcher.pass_event(event)










    def get_position(self):
        return self.pos

    def get_angle(self):
        return self.angle

    def get_radius(self):
        return self.radius

    def get_mass(self):
        return self.mass

    def get_mu(self):
        return self.mu