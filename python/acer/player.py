class Player:
    def __init__(self, position, name):
        self.position = position
        self.name = name
        self.days_to_choose = []

    def add_day(self, day_number):
        self.days_to_choose.append(day_number)

    def final_results(self):
        return ','.join(str(d) for d in sorted(self.days_to_choose))
