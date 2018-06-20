import pygame
from General import to_screen, draw
from math import cos, sin, sqrt, radians as rad
from random import randrange

class Satellite():
    def __init__(self, parent_planet, distance, direction, angle, nationality, name):
        self.parent_planet=parent_planet
        self.name = name
        self.distance = float(distance)
        self.dir = direction
        self.angle = rad(float(angle))
        self.pos = (self.distance*cos(self.angle), self.distance*sin(self.angle))
        self.rot_speed = sqrt((self.parent_planet.get_mu())/((self.distance)**3))
        if self.dir == "cw":
            self.rot_speed = -1.*self.rot_speed
        self.nationality = nationality
        self.scale = 0.15
        self.color = (randrange(50,120),randrange(20,100),randrange(20,100))
        
        #RUSSIAN SATELLITE IMAGES
        if nationality == "Roscosmos1":
            self.sprite = pygame.image.load("Sprites/Satellite_RUS.png")
            self.change_nationality("Roscosmos")
        elif nationality == "Roscosmos2":
            self.sprite = pygame.image.load("Sprites/Satellite2.png")
            self.change_nationality("Roscosmos")
        elif nationality == "Roscosmos3":
            self.sprite = pygame.image.load("Sprites/Satellite9.png")
            self.change_nationality("Roscosmos") 
        #AMERICAN SATELLITE IMAGES
        elif nationality == "NASA1":
            self.sprite = pygame.image.load("Sprites/Satellite_USA.png")
            self.change_nationality("NASA")
        elif nationality == "NASA2":
            self.sprite = pygame.image.load("Sprites/Satellite4.png")
            self.change_nationality("NASA")   
        #EUROPEAN SATELLITE IMAGES
        elif nationality == "ESA1":
            self.sprite = pygame.image.load("Sprites/Satellite_ESA.png")
            self.change_nationality("ESA")
        elif nationality == "ESA2":
            self.sprite = pygame.image.load("Sprites/Satellite5.png")
            self.change_nationality("ESA")    
        #LANDER IMAGES
        elif nationality == "LANDER1":
            self.sprite = pygame.image.load("Sprites/Satellite3.png")
            self.change_nationality("ESA")
        elif nationality == "LANDER2":
            self.sprite = pygame.image.load("Sprites/Satellite8.png")
            self.change_nationality("ESA")
        #ALIEN SPACECRAFT IMAGES
        elif nationality == "ALIEN1":
            self.sprite = pygame.image.load("Sprites/Satellite6.png")
            self.change_nationality("Alien")
        elif nationality == "ALIEN2":
            self.sprite = pygame.image.load("Sprites/Satellite7.png")
            self.change_nationality("Alien") 
        elif nationality == "ALIEN3":
            self.sprite = pygame.image.load("Sprites/Satellite10.png")
            self.change_nationality("Alien")
        elif nationality == "ALIEN4":
            self.sprite = pygame.image.load("Sprites/Satellite1.png")
            self.change_nationality("Alien") 
        #SPACE ROCK IMAGES
        elif nationality == "ROCK1":
            self.sprite = pygame.image.load("Sprites/Rock1.png")
            self.change_nationality("Space Rock")
        elif nationality == "ROCK2":
            self.sprite = pygame.image.load("Sprites/Rock2.png")
            self.change_nationality("Space Rock") 
        elif nationality == "ROCK3":
            self.sprite = pygame.image.load("Sprites/Rock3.png")
            self.change_nationality("Space Rock")
        elif nationality == "ROCK4":
            self.sprite = pygame.image.load("Sprites/Rock4.png")
            self.change_nationality("Space Rock")
        elif nationality == "MOON":
            self.sprite = pygame.image.load("Sprites/Satellite11.png")
            self.change_nationality("Space Rock") 
        elif nationality == "MOON2":
            self.sprite = pygame.image.load("Sprites/MOON2.png")
            self.change_nationality("Space Rock")
        elif nationality == "MOON3":
            self.sprite = pygame.image.load("Sprites/MOON3.png")
            self.change_nationality("Space Rock")    
        else:
            self.sprite = pygame.image.load("Sprites/backup.png")

        print "Satellite created. Orbiting all day."


    def get_pos(self):
        return self.pos

    def get_altitude(self):
        return self.distance - self.parent_planet.get_radius()

    def get_nationality(self):
        return self.nationality

    def change_nationality(self,a):
        self.nationality=a

    def get_name(self):
        return self.name

    def get_home_universe(self):
        return self.parent_planet.get_home_universe()

    def update(self, dt):
        self.angle += self.rot_speed * dt
        self.dp_x = self.distance*sin(self.angle)
        self.dp_y = self.distance*cos(self.angle)
        self.pos = (self.parent_planet.get_position()[0]+self.dp_x, self.parent_planet.get_position()[1]+self.dp_y)

    def draw(self, screen, zoom_factor, offset_pos=(0,0)):
        draw_pos = (self.pos[0]-offset_pos[0], self.pos[1]-offset_pos[1])
        self.draw_orbit(screen, zoom_factor, offset_pos)
        draw(screen, self.sprite, draw_pos, self.angle, self.scale, zoom_factor)

    def draw_orbit(self, screen, zoom_factor, offset_pos=(0,0)):
        draw_pos = (int(to_screen(self.parent_planet.get_position())[0]-offset_pos[0]*zoom_factor), int(to_screen(self.parent_planet.get_position())[1]-offset_pos[1]*zoom_factor))
        pygame.draw.circle(screen, self.color, draw_pos, int(self.distance*zoom_factor), 1)