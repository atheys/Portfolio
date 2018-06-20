import pygame

class Soundmanager():
    def __init__(self, bitrate):
        self.effects = []
        pygame.mixer.pre_init(bitrate, -16, 2, 2048)
        print "Sound manager created. Space isn't so quiet after all"

    def play_music(self, name):
        try:
            pygame.mixer.music.load("Sounds/"+name)
            pygame.mixer.music.play(-1)
        except:
            print "Music not found"