lista = ("a", "b", "c", "d", "e")

def triangle(n):
    i = n
    while True:
        if i == 1:
            return n
        i -= 1
        n += i

# def returnX(x, y):
#     if y > triangle(x) or y <= 0:
#         raise ValueError("Invalid input: Number larger than possible combinations or less than 1.")

#     if (y - x) < 1:
#         return x
#     else:
#         return returnX(x - 1, y - x)
        
  
def returnX(n):
    if n <= 0:
        raise ValueError("Only non-zero positive integers are valid inputs for this function.")
    
    x = 0
    y = 1
    counter = -1
    while x <= n:
        x += y
        y += 1
        counter = counter + 1
    return(counter)


def returnY(n, y):
    if n < 0:
        raise ValueError("Only non-zero positive integers are valid inputs for this function.")
    if n > triangle(y - 1) - 1:
        raise ValueError("Input larger than possible combinations.")
    firstItem = 1
    iteration = 0
    for j in range(0, y):
        for secondItem in range(firstItem, y):
            iteration += 1
            print ("iteration: ", iteration, "firstItem: ", firstItem - 1, "secondItem: ", secondItem)
            if iteration > n:
                return(firstItem, secondItem)
        firstItem += 1

returnY(9, 5)
    

# for i in range(0,11):
    # print("i: ", i, "return: ",returnY(i))
