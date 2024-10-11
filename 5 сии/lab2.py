from pyswip import Prolog

prolog = Prolog()

prolog.consult("first.pl")

valid_answer = {
    "y": True,
    "n": False,
    "Y": True,
    "N": False,
    "yes": True,
    "no": False,
    "Yes": True,
    "No": False,
    "YES": True,
    "NO": False
}


def get_player_name(text, question, error_msg):
    while True:
        user_input = input(text)
        user_input.lower()
        if list(prolog.query(question.format(user_input))):
            return user_input
        else:
            print(error_msg)


def get_value(question):
    res = list(prolog.query(question))
    if len(res) == 1:
        return res[0]['X']
    else:
        return res

def choose_target(name):
    players_names = list(prolog.query(f"player(X), X \\= {name}"))
    players_names = [name['X'] for name in players_names]

    current_role = get_value(f'has_role({name}, X)')
    players_roles = {}
    for player in players_names:
        role = get_value(f'has_role({player}, X)')
        health = get_value(f'player_health({player}, X)')

        players_roles[player] = (role, health)
    
    alive_players = {player: (role, health) for player, (role, health) in players_roles.items() if health > 0}

    if (current_role == "sheriff"):
        min_health_player = min(alive_players, key=lambda p: alive_players[p][1])
        print(f"Стреляй в {min_health_player}, у него меньше всего здоровья!")
        return
    elif (current_role == "renegade"):
        outlaws = {player: (role, health) for player, (role, health) in alive_players.items() if role == "outlaw"}
        if outlaws:
            min_health_outlaw = min(outlaws.items(), key=lambda item: item[1][1])
            print(f"Стреляй в {min_health_outlaw[0]}, он бандит с минимальным здоровьем!")
        else:
            sheriff_player = next((player for player, (role, health) in alive_players.items() if role == "sheriff"), None)
            print(f"Бандиты мертвы, остался шериф, стреляй в {sheriff_player}!")
    else:
            sheriff_player = next((player for player, (role, health) in alive_players.items() if role == "sheriff"), None)
            print(f"Нужно убить шерифа, стреляй в {sheriff_player}!")

        
def ask_about_shoot(name, cards):
    user_input = ""
    while user_input not in valid_answer:
        user_input = input("Вы хотите выстрелить?(y/n)\n> ")
        if user_input in valid_answer:
            if not ('bang' in cards):
                print("У тебя нечем стрелять")
                return
            if valid_answer[user_input]:
                choose_target(name)
        else:
            print("Неправильный ответ(y/n)")

def ask_about_beer(health, max_health, cards):
    if not ('beer' in cards):
        return
    if (health == max_health):
        print("У тебя максимальное здоровье, невозможно выпить пива!")
        return
    
    print("Используй карту пиво и вылечи 1 здоровье!")

def check_for_stronger_weapon(current_weapon, player_name):

    cards = get_value(f'hand_cards({player_name}, X)')

    current_weapon_power = get_value(f'weapon_power({current_weapon}, X)')

    stronger_weapons = []

    for card in cards:
        if str(card) in ['volcanic', 'remington', 'winchester', 'carabine']:
            hand_weapon_power = get_value(f'weapon_power({card}, X)')
            stronger_weapons.append((str(card), hand_weapon_power))

    if (len(stronger_weapons) == 0):
        return
    best_hand_weapon = max(stronger_weapons, key=lambda x: x[1])

    if (best_hand_weapon[1] > current_weapon_power):
        print(f"Поменяй оружие на {best_hand_weapon[0]}, оно сильнее!")


def player_info():
    player_name = get_player_name("Введите имя персонажа:\n> ", "player({})",
                                  "Данного имени нет\nПолный список имен: max, marat, egor, andrei")

    player_role = get_value(f'has_role({player_name}, X)')
    player_character = get_value(f'has_character({player_name}, X)')

    player_health = get_value(f'player_health({player_name}, X)')
    player_max_health = get_value(f'max_health({player_name}, X)')

    player_cards = get_value(f'hand_cards({player_name}, X)')
    player_cards = [str(card) for card in player_cards]

    player_weapon = get_value(f'has_weapon({player_name}, X)')
    
    print("\n**************************************")
    print("Ваши характеристики")
    print(f'Роль: {player_role}')
    print(f'Персонаж: {player_character}')
    print(f'Здоровье: {player_health}/{player_max_health}')
    print(f'Оружие:  {player_weapon}')
    print(f"Карты: {player_cards}")
    print("**************************************\n")

    ask_about_beer(player_health, player_max_health, player_cards)
    check_for_stronger_weapon(player_weapon, player_name)
    ask_about_shoot(player_name, player_cards)
    print("Доступные действия закончились, передавай ход")

if __name__ == "__main__":
    player_info()
