def solution(line):
    pos, answer = [], []

    n = len(line)

    min_x = min_y = int(1e15)
    max_x = max_y = int(-1e15)

    for i in range(n):
        a, b, e = line[i]
        for j in range(i + 1, n):
            c, d, f = line[j]

            if a * d == b * c: continue

            x = (b * f - e * d) / (a * d - b * c)
            y = (e * c - a * f) / (a * d - b * c)

            if (x == int(x) and y == int(y)):
                x = int(x)
                y = int(y)
                pos.append([x, y])

                if min_x > x: min_x = x
                if min_y > y: min_y = y
                if max_x < x: max_x = x
                if max_y < y: max_y = y


    x_len = max_x - min_x + 1
    y_len = max_y - min_y + 1

    coord = [['.'] * x_len for _ in range(y_len)]

    for star_x, star_y in pos:
        nx = star_x + abs(min_x) if max_x < 0 else star_x - min_x
        ny = star_y + abs(min_y) if min_y < 0 else star_y - min_y
        coord[ny][nx] = '*'

    for result in coord: answer.append(''.join(result))

    return answer[::-1]