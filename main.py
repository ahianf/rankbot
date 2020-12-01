class Implementation:
    """
    A class that represents an implementation of the Elo Rating System
    """

    def __init__(self, base_rating=1000):
        """
        Runs at initialization of class object.
        @param base_rating - The rating a new player would have
        """
        self.base_rating = base_rating
        self.players = []

    def __getPlayerList(self):
        """
        Returns this implementation's player list.
        @return - the list of all player objects in the implementation.
        """
        return self.players

    def getPlayer(self, name):
        """
        Returns the player in the implementation with the given name.
        @param name - name of the player to return.
        @return - the player with the given name.
        """
        for player in self.players:
            if player.name == name:
                return player
        return None

    def contains(self, name):
        """
        Returns true if this object contains a player with the given name.
        Otherwise returns false.
        @param name - name to check for.
        """
        for player in self.players:
            if player.name == name:
                return True
        return False

    def addPlayer(self, name, rating=None):
        """
        Adds a new player to the implementation.
        @param name - The name to identify a specific player.
        @param rating - The player's rating.
        """
        if rating == None:
            rating = self.base_rating

        #Check for duplicates with exact same name
        for p in self.players:
            if name == p.name:
                return

        self.players.append(_Player(name=name,rating=rating))

    def removePlayer(self, name):
        """
        Adds a new player to the implementation.
        @param name - The name to identify a specific player.
        """
        self.__getPlayerList().remove(self.getPlayer(name))


    def recordMatch(self, name1, name2, winner=None, draw=False):
        """
        Should be called after a game is played.
        @param name1 - name of the first player.
        @param name2 - name of the second player.
        """
        player1 = self.getPlayer(name1)
        player2 = self.getPlayer(name2)

        expected1 = player1.compareRating(player2)
        expected2 = player2.compareRating(player1)
        
        k = len(self.__getPlayerList()) * 42

        rating1 = player1.rating
        rating2 = player2.rating

        if draw:
            score1 = 0.5
            score2 = 0.5
        elif winner == name1:
            score1 = 1.0
            score2 = 0.0
        elif winner == name2:
            score1 = 0.0
            score2 = 1.0
        else:
            raise InputError('One of the names must be the winner or draw must be True')

        newRating1 = rating1 + k * (score1 - expected1)
        newRating2 = rating2 + k * (score2 - expected2)

        if newRating1 < 0:
            newRating1 = 0
            newRating2 = rating2 - rating1

        elif newRating2 < 0:
            newRating2 = 0
            newRating1 = rating1 - rating2

        player1.rating = newRating1
        player2.rating = newRating2

    def getPlayerRating(self, name):
        """
        Returns the rating of the player with the given name.
        @param name - name of the player.
        @return - the rating of the player with the given name.
        """
        player = self.getPlayer(name)
        return player.rating

    def getRatingList(self):
        """
        Returns a list of tuples in the form of ({name},{rating})
        @return - the list of tuples
        """
        lst = []
        for player in self.__getPlayerList():
            lst.append((player.name,player.rating))
        return lst

class _Player:
    """
    A class to represent a player in the Elo Rating System
    """

    def __init__(self, name, rating):
        """
        Runs at initialization of class object.
        @param name - TODO
        @param rating - TODO
        """
        self.name = name
        self.rating = rating

    def compareRating(self, opponent):
        """
        Compares the two ratings of the this player and the opponent.
        @param opponent - the player to compare against.
        @returns - The expected score between the two players.
        """
        return ( 1+10**( ( opponent.rating-self.rating )/400.0 ) ) ** -1

def triangle(n):
    i = n
    while True:
        if i == 1:
            return n
        i -= 1
        n += i

def itemPermutator(n, y) -> int:
    """ 
    Summary line. 
  
    Extended description of function. 
  
    Parameters: 
    n (int): Posición del índice del que se quiere obtener los dos ítems a comparar.
    
    y (int): Cantidad de ítems a comparar. Debe ser mayor o igual a 2, ya que un ítem no puede compararse a sí mismo.
  
    Returns: 
    int: Description of return value 
    """
    
    if n < 0:
        raise ValueError("Only zero and positive integers are valid inputs for this function.")
    if n > triangle(y - 1) - 1:
        raise ValueError("Out of index: Input larger than possible combinations.")
    firstItem = 1
    iteration = 0
    for j in range(y):
        for secondItem in range(firstItem, y):
            # print ("iteration: ", iteration, "firstItem: ", firstItem - 1, "secondItem: ", secondItem)
            iteration += 1
            if iteration > n:
                return(firstItem - 1, secondItem)
        firstItem += 1

def loadItems(a):
    for j in a:
        i.addPlayer(j)
        
lista = ("I Am Trying to Break Your Heart",
         "Kamera",
         "Radio Cure",
         "War on War",
         "Jesus, Etc.",
         "Ashes of American Flags",
         "Heavy Metal Drummer",
         "I'm the Man Who Loves You",
         "Pot Kettle Black",
         "Poor Places",
         "Reservations")
           
permutations = triangle(len(lista) - 1)

i = Implementation()
print(i.getRatingList())
loadItems(lista)
print(i.getRatingList())

for j in range(permutations):
    item_A, item_B = itemPermutator(j, len(lista))
    item_A = lista[item_A]
    item_B = lista[item_B]
    print("Comparando entre:", item_A, "y", item_B)
    print("Opción 1 para: ", item_A)
    print("Opción 2 para: ", item_B)
    # print("Presione 'n' para empate")
    test = str(input(""))
    if test == str(1):
        test = item_A
    if test == str(2):
        test = item_B
    # if test == str("n"):
    #     test = lista[0]
    i.recordMatch(item_A, item_B, winner=test)

#print(i.getPlayerRating("a"), i.getPlayerRating("b"))

print(i.getRatingList())




