from Universe import Universe
from HUDmanager import HUDmanager
from Menumanager import MenuManager

class Levelmanager():
    def __init__(self, screen):
        print "Level manager created. Let's order this chaos."
        self.universe = 0.0
        self.HUD = HUDmanager(screen)
        self.menu_manager = MenuManager(self)
        self.exit = False

    def load_level(self, level, resolution):
        planet_list = []
        launcher_list = []
        satellite_list = []
        rock_list = []
        filename = "level_"+str(level)+".txt"
        try:
            level_file = open("Files/"+filename)
        except:
            raise UserWarning, "Level file %s not found"%(filename)
        lines = level_file.readlines()
        level_file.close()


        for line in lines:
            if "Universe:" in line:
                line = line[11:]
                vars = line.split(",")
                zoom_factor = float(vars[0])
                zoom_speed = float(vars[1])
                max_zoom = float(vars[2])
                min_zoom = float(vars[3])
            if "Planet:" in line:
                line = line[9:].strip()
                var_list = line.split(",")
                var_list[1] = float(var_list[1])
                var_list[2] = float(var_list[2])
                var_list[3] = float(var_list[3])
                var_list[4] = var_list[4].strip()
                planet_list.append(var_list)
            if "Launcher:" in line:
                line = line[11:].strip()
                var_list = line.split(",")
                var_list[0] = float(var_list[0])
                var_list[1] = int(var_list[1])
                var_list[2] = float(var_list[2])
                fuelMax = float(var_list[2])
                var_list[3] = var_list[3].strip()
                var_list[4] = var_list[4].strip()
                var_list[5] = var_list[5].strip()
                launcher_list.append(var_list)
            if "Satellite:" in line:
                line = line[12:].strip()
                var_list = line.split(",")
                var_list[0] = float(var_list[0])
                var_list[1] = var_list[1].strip()
                var_list[2] = float(var_list[2])
                var_list[3] = var_list[3].strip()
                var_list[4] = var_list[4].strip()
                satellite_list.append(var_list)
            if "Rock:" in line:
                line = line[7:].strip()
                var_list = line.split(",")
                var_list[0] = float(var_list[0])
                var_list[1] = var_list[1].strip()
                var_list[2] = float(var_list[2])
                var_list[3] = var_list[3].strip()
                var_list[4] = var_list[4].strip()
                rock_list.append(var_list)
            if "Assignment:" in line:
                line = line[12:].strip()
                var_list = line.split(",")
                string = var_list[0]
        
        self.universe = Universe(zoom_factor, zoom_speed, max_zoom, min_zoom, resolution,string)
        self.universe.change_fuel_max(fuelMax)
        self.universe.change_level(level)

        
        for p in planet_list:
            self.universe.create_planet(*p)
        for l in launcher_list:
            self.universe.create_launcher(*l)
        for s in satellite_list:
            self.universe.create_satellite(*s)
        for t in rock_list:
            self.universe.create_satellite(*t)
            
    def update(self, dt):
        self.universe.update(dt)

    def draw(self, screen, draw_HUD):
        self.universe.draw(screen)
        self.HUD.draw(screen, self.universe, draw_HUD)

    def set_can_shoot(self, b):
        self.universe.set_can_shoot(b)

    def get_can_shoot(self):
        return self.universe.get_can_shoot()

    def get_paused(self):
        return self.menu_manager.get_paused()

    def set_paused(self, pause):
        self.menu_manager.set_paused(pause)

    def get_universe(self):
        return self.universe

    def get_intro_done(self):
        return self.menu_manager.get_intro_done()

    def set_intro_done(self, startup):
        self.menu_manager.set_intro_done(startup)

    def set_menu_active(self, index):
        self.menu_manager.set_active(index)

    def exit_game(self):
        self.exit = True

    def get_exit(self):
        return self.exit

    def draw_HUD(self, screen):
        self.HUD.overlay_HUD(screen)

    def draw_menus(self, screen):
        self.menu_manager.draw(screen)

    def pass_event(self, event):
        if not self.menu_manager.get_paused():
            self.universe.pass_event(event)
        self.menu_manager.pass_event(event)

    def is_paused(self):
        return self.menu_manager.get_paused()

    def get_launcher(self):
        return self.universe.get_launcher()

    def get_zoom_factor(self):
        return self.universe.get_zoom_factor()