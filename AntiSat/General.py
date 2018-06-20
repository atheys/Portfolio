import pygame
from math import atan2, degrees, sqrt

def to_ints(tup):
    vars = list(tup)
    for i in range(len(vars)):
        vars[i] = int(vars[i])
    return tuple(vars)

def to_screen(pos):
    display = pygame.display.Info()
    resolution = (display.current_w, display.current_h)
    return ((pos[0]+resolution[0]/2.0), (pos[1]+resolution[1]/2.0))

def draw(screen, sprite, pos, angle, scale, zoom_factor):
        rotated_sprite = pygame.transform.rotozoom(sprite, angle, zoom_factor*scale)
        rect = rotated_sprite.get_rect()
        draw_pos = (pos[0]*zoom_factor-(rect.width/2), pos[1]*zoom_factor-(rect.height/2))
        screen.blit(rotated_sprite, to_screen(draw_pos))

def get_angle_between(pos1, pos2):
    return degrees(atan2((pos2[1]-pos1[1]), (pos2[0]-pos1[0])))

def get_distance_between(pos1, pos2):
    dx = pos2[0]-pos1[0]
    dy = pos2[1]-pos1[1]
    return sqrt(dx**2+dy**2)

def scale_pos(pos, zoom_factor):
    return (pos[0]*zoom_factor, pos[1]*zoom_factor)

def get_magnitude(v):
    return sqrt(v[0]**2 + v[1]**2)

def check_collision(pos1, pos2, margin):
    if get_distance_between(pos1, pos2) < margin:
        return True
    else:
        return False