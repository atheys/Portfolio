import pygame
from General import draw

class Explosion():
    def __init__(self, pos, update_rate, nationality):
        self.t = 0.0
        self.pos = pos
        self.update_rate = update_rate
        self.nationality = nationality
        self.current_sprite = 1
        self.n_sprites = 14
        self.crop_rect = pygame.Rect(0, 0, 320, 320)
        self.draw_sprite = pygame.Surface((320,320))
        self.scale = 0.5
        self.done = False

        try:
            self.sprite = pygame.image.load("Sprites/Explosion.png")
        except:
            print "Sprite not found"
        self.draw_sprite = self.sprite.subsurface(self.crop_rect)

        try:
            self.sound = pygame.mixer.Sound("Sounds/Explosion.ogg")
            self.houston = pygame.mixer.Sound("Sounds/Houston_Problem.ogg")
            self.russian_chatter = pygame.mixer.Sound("Sounds/Russian_Chatter.ogg")
            self.russian_chatter.set_volume(0.3)
        except:
            print "Sound not found"
        self.sound.play()
        if self.nationality == "Roscosmos":
            self.russian_chatter.play()
        elif self.nationality == "NASA":
            self.houston.play()
        elif self.nationality == "ESA":
            self.houston.play()

        print "Explosion created. Something went wrong...?"

    def update(self, dt):
        if self.t > self.update_rate:
            if self.current_sprite < self.n_sprites:
                self.current_sprite += 1
            else:
                self.done = True
                #print "Explosion animation done"
            self.t -= self.update_rate
        rect_x = ((self.current_sprite-1)*320)%1280
        rect_y = ((self.current_sprite-1)//4)*320
        self.crop_rect = pygame.Rect(rect_x, rect_y, 320, 320)
        self.draw_sprite = self.sprite.subsurface(self.crop_rect)
        self.t += dt

    def is_done(self):
        if self.done:
            return True
        else:
            return False

    def draw(self, screen, zoom_factor):
        if not self.done:
            draw(screen, self.draw_sprite, self.pos, 0, self.scale, zoom_factor)