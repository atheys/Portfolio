import pygame
from General import get_distance_between, to_screen, to_ints, scale_pos

class HUDmanager():
    def __init__(self, screen):
        self.RUS_logo = pygame.image.load("Sprites/RUS_logo.png")
        self.ESA_logo = pygame.image.load("Sprites/ESA_logo.png")
        self.USA_logo = pygame.image.load("Sprites/USA_logo.png")
        self.QUESTION_logo = pygame.image.load("Sprites/QUESTION_logo.png")
        self.ALIEN_logo = pygame.image.load("Sprites/ALIEN_logo.png")
        self.launcher_sprite = pygame.image.load("Sprites/Launcher_icon.png")
        self.Earth_icon = pygame.image.load("Sprites/Earth_icon.png")
        self.Moon_icon = pygame.image.load("Sprites/Moon_icon.png")
        self.Jupiter_icon = pygame.image.load("Sprites/Jupiter_icon.png")
        self.Comet_icon = pygame.image.load("Sprites/Comet_icon.png")
        self.Neptune_icon = pygame.image.load("Sprites/Neptune_icon.png")
        self.Exoplanet1_icon = pygame.image.load("Sprites/Exoplanet1_icon.png")
        self.Exoplanet2_icon = pygame.image.load("Sprites/Exoplanet2_icon.png")
        self.Comet2_icon = pygame.image.load("Sprites/Comet2_icon.png")
        self.Exoplanet3_icon = pygame.image.load("Sprites/Exoplanet3_icon.png")
        self.Exoplanet4_icon = pygame.image.load("Sprites/Exoplanet4_icon.png")
        self.rocket_icon = pygame.image.load("Sprites/Rocket_icon.png")
        self.Rocket2_icon = pygame.image.load("Sprites/Rocket2_icon.png")
        self.Rocket3_icon = pygame.image.load("Sprites/Rocket3_icon.png")
        self.Rocket4_icon = pygame.image.load("Sprites/Rocket4_icon.png")
        self.screen = pygame.Surface(screen.get_size())
        self.screen.set_colorkey((0,0,0))

    def draw(self, screen, universe, draw_target_overlay):
        if draw_target_overlay:
            self.draw_satellite_overlay(universe)
            self.draw_rocket_overlay(universe)
            self.draw_launcher_overlay(universe)
            self.draw_planet_overlay(universe)
            self.draw_mission_info(universe)
        self.draw_general_info(universe)

    def draw_satellite_overlay(self, universe):
        cursor_pos = pygame.mouse.get_pos()
        satellite_list = universe.get_all_satellites()
        for s in satellite_list:
            s_pos = to_ints(to_screen(scale_pos(s.get_pos(), universe.get_zoom_factor())))
            if get_distance_between(s_pos, cursor_pos) < 30*universe.get_zoom_factor():
                HUD_pos = (s_pos[0]+20, s_pos[1]+5)
                HUD_block = pygame.Surface((245, 70))
                HUD_block.fill((100,100,100))
                if s.get_nationality() == "Roscosmos1" or s.get_nationality() == "Roscosmos2" or s.get_nationality() == "Roscosmos3" or s.get_nationality() == "Roscosmos":
                    HUD_block.blit(self.RUS_logo, (50-self.RUS_logo.get_width()/2,35-self.RUS_logo.get_height()/2))
                elif s.get_nationality() == "ESA1" or s.get_nationality() == "ESA2":
                    HUD_block.blit(self.ESA_logo, (50-self.ESA_logo.get_width()/2,35-self.ESA_logo.get_height()/2))
                elif s.get_nationality() == "NASA1"or s.get_nationality() == "NASA2"or s.get_nationality() == "NASA":
                    HUD_block.blit(self.USA_logo, (40-self.USA_logo.get_width()/2,35-self.USA_logo.get_height()/2))
                elif s.get_nationality() == "Alien" or s.get_nationality() == "ALIEN1" or s.get_nationality() == "ALIEN2"or s.get_nationality() == "ALIEN3" or s.get_nationality() == "ALIEN4":
                    HUD_block.blit(self.ALIEN_logo, (40-self.USA_logo.get_width()/2,35-self.USA_logo.get_height()/2))
                elif s.get_nationality() == "Space Rock" or s.get_nationality() == "ROCK1" or s.get_nationality() == "ROCK2"or s.get_nationality() == "ROCK3" or s.get_nationality() == "ROCK4":
                    HUD_block.blit(self.QUESTION_logo, (40-self.USA_logo.get_width()/2,35-self.USA_logo.get_height()/2))
                    
                font = pygame.font.SysFont("Courier", 12)
                HUD_block.blit(font.render("Name:     %s"%(s.get_name()), True, (255,255,255)), (90,5))
                HUD_block.blit(font.render("Agency:   %s"%(s.get_nationality()), True, (255,255,255)), (90,20))
                HUD_block.blit(font.render("Altitude: %s"%(s.get_altitude()), True, (255,255,255)), (90,35))
                HUD_block.blit(font.render("Right-click to target", True, (255,255,255)), (90,50))

                pygame.draw.rect(HUD_block, (255,255,255), HUD_block.get_rect(), 1)
                HUD_block.set_alpha(150)
                self.screen.blit(HUD_block, HUD_pos)

    def draw_rocket_overlay(self, universe):
        cursor_pos = pygame.mouse.get_pos()
        for rocket in universe.get_rockets():
            rocket_pos = to_ints(to_screen(scale_pos(rocket.get_pos(), universe.get_zoom_factor())))
            if get_distance_between(rocket_pos, cursor_pos) < 30*universe.get_zoom_factor():
                HUD_pos = (rocket_pos[0]+20, rocket_pos[1]+5)
                HUD_block = pygame.Surface((245, 70))
                HUD_block.fill((100,100,100))

                HUD_block.blit(self.rocket_icon, (50-self.rocket_icon.get_width()/2,35-self.rocket_icon.get_height()/2))

                font = pygame.font.SysFont("Courier", 12)
                HUD_block.blit(font.render("Fuel left: %s"%(rocket.get_fuel()), True, (255,255,255)), (90,5))
                HUD_block.blit(font.render("Space to self-destruct", True, (255,255,255)), (90,35))
                HUD_block.blit(font.render("Right-click to target", True, (255,255,255)), (90,50))

                pygame.draw.rect(HUD_block, (255,255,255), HUD_block.get_rect(), 1)
                HUD_block.set_alpha(150)
                self.screen.blit(HUD_block, HUD_pos)

    def draw_launcher_overlay(self, universe):
        cursor_pos = pygame.mouse.get_pos()
        launcher = universe.get_launcher()
        launcher_pos = to_ints(to_screen(scale_pos(launcher.get_pos(), universe.get_zoom_factor())))
        if get_distance_between(launcher_pos, cursor_pos) < 30*universe.get_zoom_factor():
            HUD_pos = (launcher_pos[0]+20, launcher_pos[1]+5)
            HUD_block = pygame.Surface((245, 70))
            HUD_block.fill((100,100,100))

            HUD_block.blit(self.launcher_sprite, (40-self.launcher_sprite.get_width()/2,35-self.launcher_sprite.get_height()/2))

            font = pygame.font.SysFont("Courier", 12)
            HUD_block.blit(font.render("Agency:       %s"%(launcher.get_nationality()), True, (255,255,255)), (90,5))
            HUD_block.blit(font.render("Rockets left: %s"%(launcher.get_rockets_left()), True, (255,255,255)), (90,20))
            HUD_block.blit(font.render("Right-click to target", True, (255,255,255)), (90,50))

            pygame.draw.rect(HUD_block, (255,255,255), HUD_block.get_rect(), 1)
            HUD_block.set_alpha(150)
            self.screen.blit(HUD_block, HUD_pos)

    def draw_planet_overlay(self, universe):
        cursor_pos = pygame.mouse.get_pos()
        planet = universe.get_planet()
        planet_pos = to_ints(to_screen(scale_pos(planet.get_pos(), universe.get_zoom_factor())))
        if get_distance_between(planet_pos, cursor_pos) < 30*universe.get_zoom_factor():
            HUD_pos = (planet_pos[0]+20, planet_pos[1]+5)
            HUD_block = pygame.Surface((245, 70))
            HUD_block.fill((100,100,100))

            if planet.get_name() == "Earth":
                HUD_block.blit(self.Earth_icon, (40-self.Earth_icon.get_width()/2,35-self.Earth_icon.get_height()/2))
            if planet.get_name() == "Moon":
                HUD_block.blit(self.Moon_icon, (40-self.Moon_icon.get_width()/2,35-self.Moon_icon.get_height()/2))
            if planet.get_name() == "Jupiter":
                HUD_block.blit(self.Jupiter_icon, (40-self.Jupiter_icon.get_width()/2,35-self.Jupiter_icon.get_height()/2))
            if planet.get_name() == "Unknown Meteor":
                HUD_block.blit(self.Comet_icon, (40-self.Comet_icon.get_width()/2,35-self.Comet_icon.get_height()/2))
            if planet.get_name() == "Neptune":
                HUD_block.blit(self.Neptune_icon, (40-self.Neptune_icon.get_width()/2,35-self.Neptune_icon.get_height()/2))
            if planet.get_name() == "Exoplanet I":
                HUD_block.blit(self.Exoplanet1_icon, (40-self.Exoplanet1_icon.get_width()/2,35-self.Exoplanet1_icon.get_height()/2))
            if planet.get_name() == "Exoplanet II":
                HUD_block.blit(self.Exoplanet2_icon, (40-self.Exoplanet2_icon.get_width()/2,35-self.Exoplanet2_icon.get_height()/2))
            if planet.get_name() == "Exoplanet III":
                HUD_block.blit(self.Exoplanet3_icon, (40-self.Exoplanet3_icon.get_width()/2,35-self.Exoplanet3_icon.get_height()/2))
            if planet.get_name() == "Unkown Exometeor":
                HUD_block.blit(self.Comet2_icon, (40-self.Comet2_icon.get_width()/2,35-self.Comet2_icon.get_height()/2))
            if planet.get_name() == "Exoplanet IV":
                HUD_block.blit(self.Exoplanet4_icon, (40-self.Exoplanet4_icon.get_width()/2,35-self.Exoplanet4_icon.get_height()/2))

            font = pygame.font.SysFont("Courier", 12)
            HUD_block.blit(font.render("Name:    %s"%(planet.get_name()), True, (255,255,255)), (90,5))
            HUD_block.blit(font.render("Right-click to target", True, (255,255,255)), (90,50))

            pygame.draw.rect(HUD_block, (255,255,255), HUD_block.get_rect(), 1)
            HUD_block.set_alpha(150)
            self.screen.blit(HUD_block, HUD_pos)

    def draw_general_info(self, universe):
        planet = universe.get_planet()
        launcher = universe.get_launcher()
        font = pygame.font.SysFont("Courier", 16)
        info_block = pygame.Surface((180, 260))
        info_pos = (self.screen.get_width()-info_block.get_width()-10, 10)

        info_block.fill((1,1,1))

        font.set_bold(True)
        info_block.blit(font.render("Game Info", True, (255,255,255)), (5,0))

        info_block.blit(font.render("Planet:", True, (255,255,255)), (5,30))
        info_block.blit(font.render("Rockets:", True, (255,255,255)), (5,70))
        info_block.blit(font.render("Fuel used:", True, (255,255,255)), (5,110))
        info_block.blit(font.render("Satellites:", True, (255,255,255)), (5,150))
        info_block.blit(font.render("Level:", True, (255,255,255)), (5,190))

        font.set_bold(False)

        info_block.blit(font.render(planet.get_name(), True, (255,255,255)), (5,45))
        info_block.blit(font.render(str(launcher.get_rockets_left()), True, (255,255,255)), (5,85))
        info_block.blit(font.render(str(universe.get_fuel_used()), True, (255,255,255)), (5,125))
        info_block.blit(font.render(str(len(universe.get_satellites())), True, (255,255,255)), (5,165))
        info_block.blit(font.render(str(universe.level), True, (255,255,255)), (5,205))

        info_block.blit(font.render("Press P to pause", True, (255,255,255)), (5,235))

        pygame.draw.rect(info_block, (255,255,255), info_block.get_rect(), 1)
        self.screen.blit(info_block, info_pos)
    
    def draw_mission_info(self, universe):
        font = pygame.font.SysFont("Courier", 16)
        info_block = pygame.Surface((425, 20))
        info_pos = (10,self.screen.get_height()-info_block.get_height()-10)
        
        font.set_bold(True)
        info_block.blit(font.render("Mission:", True, (255,255,255)), (5,0))
        
        font.set_bold(False)
        info_block.blit(font.render(str(universe.get_assignment()), True, (255,255,255)), (105,0))
        
        pygame.draw.rect(info_block, (255,255,255), info_block.get_rect(), 1)
        self.screen.blit(info_block, info_pos)
        
    def overlay_HUD(self, surface):
        surface.blit(self.screen, (0,0))
        self.screen.fill((0,0,0))