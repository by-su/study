def solution(n):

    # n * n 2차원 배열 생성
    snail = [[0] * i for i in range(1, n + 1)]

    # 반 시계 방향의 나선형 형태로 배열을 채워나간다.
    dx = [0, 1, -1]
    dy = [1, 0, -1]

    x = y = angle = 0
    cnt = 1

    size = (n + 1) * n // 2 # len(snail)
    while cnt <= size:
        snail[y][x] = cnt
        ny = y + dy[angle]
        nx = x + dx[angle]
        cnt += 1

        if 0 <= ny < n and 0 <= nx <= ny and snail[ny][nx] == 0:
            y,x = ny,nx
        else:
            angle = (angle + 1) % 3
            y += dy[angle]
            x += dx[angle]

    return [i for j in snail for i in j]