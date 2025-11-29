def solution(rows, columns, queries):
    array = [[i * columns + j + 1 for j in range(columns)] for i in range(rows)]

    answer = []
    for query in queries:
        r1 = query[0] - 1
        c1 = query[1] - 1
        r2 = query[2] - 1
        c2 = query[3] - 1

        first = array[r1][c1]
        min_value = first

        for r in range(r1, r2):
            array[r][c1] = array[r + 1][c1]
            min_value = min(array[r + 1][c1], min_value)

        for c in range(c1, c2):
            array[r2][c] = array[r2][c + 1]
            min_value = min(array[r2][c + 1], min_value)

        for r in range(r2, r1, -1):
            array[r][c2] = array[r - 1][c2]
            min_value = min(array[r - 1][c2], min_value)

        for c in range(c2, c1 + 1, -1):
            array[r1][c] = array[r1][c - 1]
            min_value = min(array[r1][c], min_value)

        array[r1][c1 + 1] = first
        answer.append(min_value)

    return answer





