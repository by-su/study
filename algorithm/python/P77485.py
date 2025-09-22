

def solution(rows, columns, queries):
     matrix = [[i * columns + j + 1 for j in range(columns)] for i in range(rows)]
     result = []
     for r1, c1, r2, c2 in queries:
         result.append(rotate(r1 - 1, c1 - 1 , r2 - 1, c2 -1, matrix))

     return result


def rotate(row1, col1, row2, col2, matrix):
    first = matrix[row1][col1]
    min_value = first

    # 왼쪽
    for k in range(row1, row2):
        matrix[k][col1] = matrix[k + 1][col1]
        min_value = min(min_value, matrix[k + 1][col1])

    # 아래
    for k in range(col1, col2):
        matrix[row2][k] = matrix[row2][k + 1]
        min_value = min(min_value, matrix[row2][k + 1])

    # 오른쪽
    for k in range(row2, row1, -1):
        matrix[k][col2] = matrix[k - 1][col2]
        min_value = min(min_value, matrix[k - 1][col2])

    # 위
    for k in range(col2, col1 + 1, - 1):
        matrix[row1][k] = matrix[row1][k - 1]
        min_value = min(min_value, matrix[row1][k - 1])

    matrix[row1][col1 + 1] = first
    return min_value


if __name__ == "__main__":
    main()