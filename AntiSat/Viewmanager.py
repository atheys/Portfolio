import pygame
from General import to_screen, scale_pos

class Viewmanager():
    def __init__(self):
        self.views = []
        print "View manager created. Ready to shed some light on the darkness of space."

    def add_view(self, pos, size):
        self.views.append(View(pos, size))

    def set_target(self, index, target, surface):
        self.views[index].set_target(target, surface)

    def draw(self, screen, planet, rockets, zoom_factor):
        for v in self.views:
            v.draw(screen, planet, rockets, zoom_factor)

class View():
    def __init__(self, pos, size):
        self.target = (0,0)
        self.target_surface = pygame.Surface((1,1))
        self.pos = pos
        self.size = size

    def set_target(self, target, surface):
        self.target = target
        self.target_surface = surface

    def draw(self, screen, planet, rockets, zoom_factor):
        try:
            mini_view = pygame.Surface(self.size)
            offset_x = int((screen.get_width()/2-self.size[0]/2)/zoom_factor)
            offset_y = int((screen.get_height()/2-self.size[1]/2)/zoom_factor)
            offset_pos = (self.target.get_pos()[0]+offset_x, self.target.get_pos()[1]+offset_y)
            font = pygame.font.SysFont("Courier", 16)
            font.set_bold(True)
            planet.draw(mini_view, zoom_factor, offset_pos=offset_pos)
            for rocket in rockets:
                rocket.draw(mini_view, zoom_factor, offset_pos=offset_pos)
            pygame.draw.rect(mini_view, (255, 255, 255), mini_view.get_rect(), 1)
            mini_view.blit(font.render("Mini view", True, (255,255,255)), (5,0))
            screen.blit(mini_view, self.pos)
        except AttributeError:
            #print "Target not yet initialized"
            pass