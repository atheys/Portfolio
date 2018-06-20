import pygame
from Soundmanager import Soundmanager
from Levelmanager import Levelmanager

title = "Anti-Sat"
fps = 80
resolution = (800,600)
full_screen = True

pygame.display.set_caption(title)

def main():

    sound_manager = Soundmanager(44100)
    pygame.init()
    sound_manager.play_music("Ambient.mp3")
    exit = False

    for i in range(1,11):

        if full_screen:
            display = pygame.display.Info()
            resolution = (display.current_w, display.current_h)
            main_surface = pygame.display.set_mode(resolution, pygame.FULLSCREEN)
        else:
            resolution = (800,600)
            main_surface = pygame.display.set_mode(resolution)

        level_manager = Levelmanager(main_surface)
        level_manager.load_level(i, resolution)
        level_manager.universe.change_level(i)

        clock = pygame.time.Clock()
        running = True

        pygame.mouse.set_cursor(*pygame.cursors.broken_x)

        while running:

            dt = clock.tick_busy_loop(fps)/1000.0
            level_manager.update(dt)

            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    level_manager.exit_game()
                    pygame.quit()
                if event.type == pygame.KEYDOWN:
                    if event.key == pygame.K_ESCAPE:
                        level_manager.exit_game()
                        exit = True
                    if event.key == pygame.K_r:
                        level_manager.load_level(i, resolution)



                    if event.key == pygame.K_0:
                        level_manager.load_level(10, resolution)
                        level_manager.set_can_shoot(True)
                    if event.key == pygame.K_1:
                        level_manager.load_level(1, resolution)
                        level_manager.set_can_shoot(True)
                    if event.key == pygame.K_2:
                        level_manager.load_level(2, resolution)
                        level_manager.set_can_shoot(True)
                    if event.key == pygame.K_3:
                        level_manager.load_level(3, resolution)
                        level_manager.set_can_shoot(True)
                    if event.key == pygame.K_4:
                        level_manager.load_level(4, resolution)
                        level_manager.set_can_shoot(True)
                    if event.key == pygame.K_5:
                        level_manager.load_level(5, resolution)
                        level_manager.set_can_shoot(True)
                    if event.key == pygame.K_6:
                        level_manager.load_level(6, resolution)
                        level_manager.set_can_shoot(True)
                    if event.key == pygame.K_7:
                        level_manager.load_level(7, resolution)
                        level_manager.set_can_shoot(True)
                    if event.key == pygame.K_8:
                        level_manager.load_level(8, resolution)
                        level_manager.set_can_shoot(True)
                    if event.key == pygame.K_9:
                        level_manager.load_level(9, resolution)
                        level_manager.set_can_shoot(True)

                level_manager.pass_event(event)

            if level_manager.get_exit():
                running = False
                exit = True
            if len(level_manager.universe.get_satellites())==0:
                running = False

            if not level_manager.get_intro_done():
                level_manager.set_menu_active(1)
                level_manager.draw(main_surface, False)
                level_manager.draw_menus(main_surface)


            paused = level_manager.get_paused()

            if not paused:
                level_manager.update(dt)
                main_surface.fill((0,0,0))
                level_manager.draw(main_surface, True)
            level_manager.draw_HUD(main_surface)
            level_manager.draw_menus(main_surface)

            pygame.display.flip()
            pygame.event.pump()

            pygame.display.set_caption(str(clock.get_fps()))

        if exit:
            break

    pygame.quit()
 
main()