import math

A = 17 # Set to Arm.ARM_RETRACTED_LENGTH
H = 18 # Set to Arm.HAND_LENGTH
SH = 17 # Set to Arm.SHOULDER_HEIGHT

def vectorize(s, e, t, w):
    a = A + e
    x = a * math.sin(math.radians(s)) + H * math.sin(math.radians(s - w))
    y = a * math.cos(math.radians(s)) + H * math.cos(math.radians(s - w)) + SH
    return f'({x:7.2f}, {y:7.2f}), {(s - w):7.2f}, {t:7.2f}'

entries = [
    ('LOW_FRONT_CONE',        -105,        0,          90,    -15),
    ('LOW_FRONT_CUBE',        -105,        0,          90,    -15),
    ('LOW_BACK_CONE',          105,        0,         -90,     15),
    ('LOW_BACK_CUBE',          105,        0,         -90,     15),
    ('MID_FRONT_CONE',         -54,      6.5,          90,     -0),
    ('MID_FRONT_CUBE',         -60,        0,          90,     40),
    ('MID_BACK_CONE',           54,      6.5,         -90,      0),
    ('MID_BACK_CUBE',           60,        0,         -90,    -40),
    ('HIGH_FRONT_CONE',        -52,       26,          90,      5),
    ('HIGH_FRONT_CUBE',        -55,       18,          90,     35),
    ('HIGH_BACK_CONE',          52,       26,         -90,     -5),
    ('HIGH_BACK_CUBE',          55,       18,         -90,    -35),
    ('SUBSTATION_FRONT_CONE',   41,        0,         -90,    -45),
    ('SUBSTATION_FRONT_CUBE',   41,        0,         -90,    -45),
    ('SUBSTATION_BACK_CONE',   -41,        0,          90,     45),
    ('SUBSTATION_BACK_CUBE',   -41,        0,          90,     45),
    ('GROUND_FRONT_CONE',      110,        0,         -90,     15),
    ('GROUND_FRONT_CUBE',       90,        0,           0,    -45),
    ('GROUND_BACK_CONE',      -110,        0,          90,    -15),
    ('GROUND_BACK_CUBE',       -90,        0,          -0,     45)
]

print('Position                 X         Y         Angle    Twist')
print('-----------------------------------------------------------')

for name, s, e, t, w in entries:
    print((name + ": ").ljust(23, ' ') + vectorize(s, e, t, w))
