:- dynamic hand_cards/2.
:- dynamic player_health/2. 

% Игроки в настольную игру
player(max).
player(marat).
player(egor).
player(andrei).

% Роли
role(sheriff).
role(renegade).
role(outlaw).
role(deputy).

% Оружие
weapon(volcanic).
weapon(remington).
weapon(winchester).
weapon(carabine).

% Сила оружия
weapon_power(volcanic, 1).
weapon_power(remington, 1).
weapon_power(winchester, 2).
weapon_power(carabine, 3).


% Персонажи
character(bart_cassidy).
character(black_jack).
character(calamity_janet).
character(jesse_jones).

% Карты
card(bang).
card(miss).
card(beer).
card(volcanic).
card(remington).
card(winchester).
card(carabine).

% Здоровье игроков
player_health(max, 5).
player_health(marat, 4).
player_health(egor, 3).
player_health(andrei, 4).

% Карты в руке у игроков
hand_cards(max, [bang, miss, beer, carabine]).
hand_cards(marat, [bang, miss, bang, bang, bang, bang]).
hand_cards(andrei, [bang, beer]).
hand_cards(egor, []).

% Роли игроков
has_role(max, sheriff).
has_role(marat, renegade).
has_role(egor, outlaw).
has_role(andrei, outlaw).

% Персонаж игроков
has_character(max, bart_cassidy).
has_character(marat, black_jack).
has_character(andrei, calamity_janet).
has_character(egor, jesse_jones).

% Оружие игроков
has_weapon(max, volcanic).
has_weapon(andrei, remington).
has_weapon(marat, winchester).
has_weapon(egor, carabine).

% Максимальное здоровье игрока
max_health(Player, MaxHealth) :-
    (   has_role(Player, sheriff) -> MaxHealth is 5 
    ;   MaxHealth is 4). 

% Использовал карту "Мимо!"
avoided_hit(Target) :-
    write(Target), write(' avoided getting hit').

% Использовал карту "Бэнг" на игрока
shoot(Shooter, Target) :-
    hand_cards(Shooter, CardsS),
    member(bang, CardsS), 
    has_weapon(Shooter, Weapon),
    weapon_power(Weapon, Damage),
    select(bang, CardsS, NewCardsS),
    retract(hand_cards(Shooter, CardsS)),
    asserta(hand_cards(Shooter, NewCardsS)),
    write(Shooter), write(' shoots at '), write(Target), nl,
    hand_cards(Target, Cards),
    (member(miss, Cards) ->
        select(miss, Cards, NewCards),
        retract(hand_cards(Target, Cards)),
        asserta(hand_cards(Target, NewCards)),
        avoided_hit(Target);

        take_damage(Target, Damage)
    ).

% Использовал карту "Пиво"
use_beer(Player) :-
    hand_cards(Player, Cards),
    member(beer, Cards),
    player_health(Player, Health),
    max_health(Player, MaxHealth),
    Health < MaxHealth,
    !,
    retract(hand_cards(Player, Cards)),
    select(beer, Cards, NewCards),
    asserta(hand_cards(Player, NewCards)),
    write(Player), write(' used beer and gained 1 health'), nl,
    NewHealth is Health + 1,
    retract(player_health(Player, Health)),
    asserta(player_health(Player, NewHealth)).


% Игрок получил урон
take_damage(Player, Damage) :-
    player_health(Player, Health),
    NewHealth is max(Health - Damage, 0),
    retract(player_health(Target, _)),
    asserta(player_health(Target, NewHealth)),
    write(Target), write(' taking '), write(Damage), write(' damage.'), nl,
    check_if_dead(Player).


% Проверка, жив ли игрок
check_if_dead(Player) :-
    player_health(Player, Health),
    (   Health > 0 ->  write(Player), write(' is alive.'), nl;   
    (       hand_cards(Player, Cards), member(beer, Cards), use_beer(Player);
        write(Player), write(' is dead.'), nl,
        check_game_over
    )
).

% Проверка, закончилась ли игра
check_game_over :-
    findall(Role, (player(P), has_role(P, Role), player_health(P, Health), Health > 0), RolesAlive),
    (   member(outlaw, RolesAlive), \+ member(sheriff, RolesAlive) ->
        write('Outlaws win!'), nl;
        member(renegade, RolesAlive), \+ member(outlaw, RolesAlive), \+ member(sheriff, RolesAlive), \+ member(deputy, RolesAlive)  ->
        write('Renegade wins!'), nl;
        member(sheriff, RolesAlive), \+ member(outlaw, RolesAlive), \+ member(renegade, RolesAlive) ->
        write('Sheriff and Deputy wins!'), nl;
        
        
        write('Game continues!'), nl
    ).
