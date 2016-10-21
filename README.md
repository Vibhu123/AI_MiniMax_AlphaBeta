# AI_MiniMax_AlphaBeta

This repository contains the well known AI algorithms Minimax and AlphaBeta implementation on a square board game.
The input to the game is given in the form of a square grid where the number of a cell corresponds to the value the given player
holds at that position i.e. either 'X' or 'O'. 
The output consists of two values "Stake" and "Raid" where "Stake" corresponds to keeping the player at that position and adding
the score to the total value for the player so far.
"Raid" corresponds to taking the adjacent square values i.e. up,down,left and right where in one of the corresponding positions the
player has got support for itself. Like if the next turn is of 'X' the below move will correspond to a "Raid" thus acquiring the squares
occupied by the opponent player, here its 'O'.

"Stake" (Assuming next turn is of 'X'). Here if 'X' moves at any of the corresponding empty locations it will be a stake corresponding to the
value the square cell will hold.
       .X.
       ...
       .O.
"Raid" (Assuming the next turn is of 'X'). The below move will correspond to a "Raid" depending upon the square values that will get added for the
corresponding player. Here if 'X' takes the position of second row, second column then because its supported by 'X' from one of the positions i.e. 
top, down, bottom or up then it will take over 'O'.

      .X.                           .X.
      ..O              ------>      .XX   
      ...                           ...
