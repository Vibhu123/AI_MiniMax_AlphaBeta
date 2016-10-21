import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class homework1 {
	
	static int n;
	public static void minMaxBestMove(String[][] arr,int depth,String player,boolean playerFlag) throws FileNotFoundException, UnsupportedEncodingException
	{
		String bestMove="",samePlayer="X",opponentPlayer="O",overallBM="",a="";
		int x=0,y = 0;
		Map<Integer,String> map=new HashMap<Integer,String>();
		int bestVal=Integer.MIN_VALUE;
		int moveVal=Integer.MIN_VALUE;
		
		PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
		
		
		for(int i=0;i<arr.length;i++)
		{
			for(int j=0;j<arr[i].length;j++)
			{
				boolean top=false,left=false,bottom=false,right=false;
				String topStr="",leftStr="",bottomStr="",rightStr="";
				//If cell is empty
				if(!arr[i][j].contains(","))
				{
					//make the move
					arr[i][j]=samePlayer+","+arr[i][j];
					bestMove="Stake";
					//check for top
					if(coordIsValid(i-1, j))
					{
						if(arr[i-1][j].contains(opponentPlayer))
						{
							if(coordIsValid(i,j-1)&&arr[i][j-1].contains(samePlayer)||
									coordIsValid(i+1, j)&&arr[i+1][j].contains(samePlayer)||
									coordIsValid(i, j+1)&&arr[i][j+1].contains(samePlayer))
							{
								topStr=arr[i-1][j];
								arr[i-1][j]=samePlayer+","+arr[i-1][j].split(",")[1];
								bestMove="Raid";
								top=true;
							}
						}
					}
					
					//check for left
					if(coordIsValid(i, j-1))
					{
						if(arr[i][j-1].contains(opponentPlayer))
						{
							if(coordIsValid(i-1,j)&&arr[i-1][j].contains(samePlayer)||
									coordIsValid(i+1, j)&&arr[i+1][j].contains(samePlayer)||
									coordIsValid(i, j+1)&&arr[i][j+1].contains(samePlayer))
							{
							leftStr=arr[i][j-1];	
							arr[i][j-1]=samePlayer+","+arr[i][j-1].split(",")[1];
							bestMove="Raid";
							left=true;
							}
						}
					
					}
					//check for bottom
					if(coordIsValid(i+1, j))
					{
						if(arr[i+1][j].contains(opponentPlayer))
						{
							if(coordIsValid(i,j-1)&&arr[i][j-1].contains(samePlayer)||
									coordIsValid(i-1, j)&&arr[i-1][j].contains(samePlayer)||
									coordIsValid(i, j+1)&&arr[i][j+1].contains(samePlayer))
							{
								bottomStr=arr[i+1][j];
							arr[i+1][j]=samePlayer+","+arr[i+1][j].split(",")[1];
							bestMove="Raid";
							bottom=true;
							}
						}
					
					}
					//check for right
					if(coordIsValid(i, j+1))
					{
						if(arr[i][j+1].contains(opponentPlayer))
						{
							if(coordIsValid(i,j-1)&&arr[i][j-1].contains(samePlayer)||
									coordIsValid(i+1, j)&&arr[i+1][j].contains(samePlayer)||
									coordIsValid(i-1, j)&&arr[i-1][j].contains(samePlayer))
							{
								rightStr=arr[i][j+1];
							arr[i][j+1]=samePlayer+","+arr[i][j+1].split(",")[1];
							bestMove="Raid";
							right=true;
							}
						}
					}
					
					if(depth==1)
					{
						moveVal=miniMax(arr,1,samePlayer,depth);
					}
					else
					{
						moveVal=miniMax(arr,1,opponentPlayer,depth);
					}
					if(moveVal>=bestVal)
					{
						if(!map.containsKey(moveVal))
						{
							bestVal=moveVal;
							x=i;
							y=j;
							overallBM=bestMove;
							map.put(bestVal, overallBM);
						}
						else
						{
							if(map.get(moveVal).contentEquals("Raid")&&bestMove.contentEquals("Stake"))
							{
								bestVal=moveVal;
								x=i;
								y=j;
								overallBM=bestMove;
								map.put(bestVal, "Stake");
							}
						}
					}
					arr[i][j]=arr[i][j].split(",")[1];
					if(top)
					{
						arr[i-1][j]=topStr;
					}
					if(left)
					{
						arr[i][j-1]=leftStr;
					}
					if(bottom)
					{
						arr[i+1][j]=bottomStr;
					}
					if(right)
					{
						arr[i][j+1]=rightStr;
					}
				}
			}
		}
		Character y1=(char)(y+65);
		String y2=y1.toString();
		System.out.println(y1+"->"+(x+1)+overallBM);
		if(overallBM.contentEquals("Stake")&&playerFlag)
		{
			for(int i=0;i<arr.length;i++)
			{
				for(int j=0;j<arr[i].length;j++)
				{
					if(arr[i][j].contains("X"))
					{
						a=a+"O";
					}
					else if(arr[i][j].contains("O"))
					{
						a=a+"X";
					}
					else
					{
						if(i==x&&j==y)
						{
							a=a+"O";
						}
						else
						{
							a=a+".";
						}
					}
				}
				a=a+"\n";
			}
		}
		else if(overallBM.contains("Stake")&&!playerFlag)
		{
			for(int i=0;i<arr.length;i++)
			{
				for(int j=0;j<arr[i].length;j++)
				{
					if(arr[i][j].contains("X"))
					{
						a=a+"X";
					}
					else if(arr[i][j].contains("O"))
					{
						a=a+"O";
					}
					else
					{
						if(i==x&&j==y)
						{
							a=a+"X";
						}
						else
						{
							a=a+".";
						}
					}
				}
				a=a+"\n";
			}
		}
		else if(overallBM.contains("Raid")&&playerFlag)
		{
			//First populate the board
			for(int i=0;i<arr.length;i++)
			{
				for(int j=0;j<arr[i].length;j++)
				{
					if(i==x&&j==y)
					{
						//check for top
						if(coordIsValid(i-1, j)&&arr[i-1][j].contains(","))
							arr[i-1][j]="X";
						//check for left
						if(coordIsValid(i, j-1)&&arr[i][j-1].contains(","))
							arr[i][j-1]="X";
						//check for bottom
						if(coordIsValid(i+1, j)&&arr[i+1][j].contains(","))
							arr[i+1][j]="X";
						//check for right
						if(coordIsValid(i, j+1)&&arr[i][j+1].contains(","))
							arr[i][j+1]="X";
					}
				}
			}	
				for(int i=0;i<arr.length;i++)
				{
					for(int j=0;j<arr[i].length;j++)
					{
						if(arr[i][j].contains("X"))
						{
							a=a+"O";
						}
						else if(arr[i][j].contains("O"))
						{
							a=a+"X";
						}
						else
						{
							if(i==x&&j==y)
							{
								a=a+"O";
							}
							else
							{
								a=a+".";
							}
						}
					}
					a=a+"\n";
				}
				
		}
		else if(overallBM.contains("Raid")&&!playerFlag)
		{
			//First populate the board
			for(int i=0;i<arr.length;i++)
			{
				for(int j=0;j<arr[i].length;j++)
				{
					if(i==x&&j==y)
					{
						//check for top
						if(coordIsValid(i-1, j)&&arr[i-1][j].contains(","))
							arr[i-1][j]="X";
						//check for left
						if(coordIsValid(i, j-1)&&arr[i][j-1].contains(","))
							arr[i][j-1]="X";
						//check for bottom
						if(coordIsValid(i+1, j)&&arr[i+1][j].contains(","))
							arr[i+1][j]="X";
						//check for right
						if(coordIsValid(i, j+1)&&arr[i][j+1].contains(","))
							arr[i][j+1]="X";
					}
				}
			}	
				for(int i=0;i<arr.length;i++)
				{
					for(int j=0;j<arr[i].length;j++)
					{
						if(arr[i][j].contains("X"))
						{
							a=a+"X";
						}
						else if(arr[i][j].contains("O"))
						{
							a=a+"O";
						}
						else
						{
							if(i==x&&j==y)
							{
								a=a+"X";
							}
							else
							{
								a=a+".";
							}
						}
					}
					a=a+"\n";
				}
				
		}
		a=a.substring(0, a.length()-1);
		writer.write(y2+(x+1)+" "+overallBM+"\n");
		writer.write(a);
		writer.close();
		
		
	}
	
	public static int miniMax(String[][] arr,int currDepth,String player,int depth)
	{
	
		if(currDepth==depth||isBoardFull(arr))
		{
			return evaluate(arr,player);
		}
		//move for maximizer
		if(player.contentEquals("X"))
		{
			String samePlayer="X",opponentPlayer="O";
			int best=Integer.MIN_VALUE;
			for(int i=0;i<arr.length;i++)
			{
				for(int j=0;j<arr[i].length;j++)
				{
					boolean top=false,left=false,bottom=false,right=false;
					String topStr="",leftStr="",bottomStr="",rightStr="";
					//If cell is empty
					if(!(arr[i][j].contains(",")))
					{
						//make the move
						arr[i][j]=samePlayer+","+arr[i][j];
						//check for top
						if(coordIsValid(i-1, j))
						{
							if(arr[i-1][j].contains(opponentPlayer))
							{
								if(coordIsValid(i,j-1)&&arr[i][j-1].contains(samePlayer)||
										coordIsValid(i+1, j)&&arr[i+1][j].contains(samePlayer)||
										coordIsValid(i, j+1)&&arr[i][j+1].contains(samePlayer))
								{
									
									topStr=arr[i-1][j];
									arr[i-1][j]=samePlayer+","+arr[i-1][j].split(",")[1];
									top=true;
								}
							}
						}

						//check for left
						if(coordIsValid(i, j-1))
						{
							if(arr[i][j-1].contains(opponentPlayer))
							{
								if(coordIsValid(i-1,j)&&arr[i-1][j].contains(samePlayer)||
										coordIsValid(i+1, j)&&arr[i+1][j].contains(samePlayer)||
										coordIsValid(i, j+1)&&arr[i][j+1].contains(samePlayer))
								{
									
									leftStr=arr[i][j-1];
									arr[i][j-1]=samePlayer+","+arr[i][j-1].split(",")[1];
									left=true;
								}
							}

						}
						//check for bottom
						if(coordIsValid(i+1, j))
						{
							if(arr[i+1][j].contains(opponentPlayer))
							{
								if(coordIsValid(i,j-1)&&arr[i][j-1].contains(samePlayer)||
										coordIsValid(i-1, j)&&arr[i-1][j].contains(samePlayer)||
										coordIsValid(i, j+1)&&arr[i][j+1].contains(samePlayer))
								{
									bottomStr=arr[i+1][j];
									arr[i+1][j]=samePlayer+","+arr[i+1][j].split(",")[1];
									bottom=true;
								}
							}

						}
						//check for right
						if(coordIsValid(i, j+1))
						{
							if(arr[i][j+1].contains(opponentPlayer))
							{
								if(coordIsValid(i,j-1)&&arr[i][j-1].contains(samePlayer)||
										coordIsValid(i+1, j)&&arr[i+1][j].contains(samePlayer)||
										coordIsValid(i-1, j)&&arr[i-1][j].contains(samePlayer))
								{
									rightStr=arr[i][j+1];
									arr[i][j+1]=samePlayer+","+arr[i][j+1].split(",")[1];
									right=true;
								}
							}
						}
						if(currDepth+1==depth)
						{
							best=Math.max(best, evaluate(arr,samePlayer));
						}
						else
							best=Math.max(best, miniMax(arr,currDepth+1,opponentPlayer,depth));
						
						//undo the move
						arr[i][j]=arr[i][j].split(",")[1];
						if(top)
						{
							arr[i-1][j]=topStr;
						}
						if(left)
						{
							arr[i][j-1]=leftStr;
						}
						if(bottom)
						{
							arr[i+1][j]=bottomStr;
						}
						if(right)
						{
							arr[i][j+1]=rightStr;
						}
					}
				}	
			}
			return best;
		}
		else//move for minimizer
		{
			String samePlayer="O",opponentPlayer="X";
			int best=Integer.MAX_VALUE;
			for(int i=0;i<arr.length;i++)
			{
				for(int j=0;j<arr[i].length;j++)
				{
					boolean top=false,left=false,bottom=false,right=false;
					String topStr="",leftStr="",bottomStr="",rightStr="";
					
					//If cell is empty
					if(!(arr[i][j].contains(",")))
					{
						//make the move
						arr[i][j]=samePlayer+","+arr[i][j];
						//check for top
						if(coordIsValid(i-1, j))
						{
							if(arr[i-1][j].contains(opponentPlayer))
							{
								if(coordIsValid(i,j-1)&&arr[i][j-1].contains(samePlayer)||
										coordIsValid(i+1, j)&&arr[i+1][j].contains(samePlayer)||
										coordIsValid(i, j+1)&&arr[i][j+1].contains(samePlayer))
								{
									topStr=arr[i-1][j];
									arr[i-1][j]=samePlayer+","+arr[i-1][j].split(",")[1];
									top=true;
								}
							}
						}

						//check for left
						if(coordIsValid(i, j-1))
						{
							if(arr[i][j-1].contains(opponentPlayer))
							{
								if(coordIsValid(i-1,j)&&arr[i-1][j].contains(samePlayer)||
										coordIsValid(i+1, j)&&arr[i+1][j].contains(samePlayer)||
										coordIsValid(i, j+1)&&arr[i][j+1].contains(samePlayer))
								{
									leftStr=arr[i][j-1];
									arr[i][j-1]=samePlayer+","+arr[i][j-1].split(",")[1];
									left=true;
								}
							}

						}
						//check for bottom
						if(coordIsValid(i+1, j))
						{
							if(arr[i+1][j].contains(opponentPlayer))
							{
								if(coordIsValid(i,j-1)&&arr[i][j-1].contains(samePlayer)||
										coordIsValid(i-1, j)&&arr[i-1][j].contains(samePlayer)||
										coordIsValid(i, j+1)&&arr[i][j+1].contains(samePlayer))
								{
									bottomStr=arr[i+1][j];
									arr[i+1][j]=samePlayer+","+arr[i+1][j].split(",")[1];
									bottom=true;
								}
							}

						}
						//check for right
						if(coordIsValid(i, j+1))
						{
							if(arr[i][j+1].contains(opponentPlayer))
							{
								if(coordIsValid(i,j-1)&&arr[i][j-1].contains(samePlayer)||
										coordIsValid(i+1, j)&&arr[i+1][j].contains(samePlayer)||
										coordIsValid(i-1, j)&&arr[i-1][j].contains(samePlayer))
								{
									rightStr=arr[i][j+1];
									arr[i][j+1]=samePlayer+","+arr[i][j+1].split(",")[1];
									right=true;
								}
							}
						}

						best=Math.min(best, miniMax(arr,currDepth+1,opponentPlayer,depth));

						//undo the move
						arr[i][j]=arr[i][j].split(",")[1];
						if(top)
						{
							arr[i-1][j]=topStr;
						}
						if(left)
						{
							arr[i][j-1]=leftStr;
						}
						if(bottom)
						{
							arr[i+1][j]=bottomStr;
						}
						if(right)
						{
							arr[i][j+1]=rightStr;
						}
					}
				}	
			}
			return best;
		}
		
	}
	
	public static void alphaBeta(String[][] arr,int depth,String player,boolean playerFlag) throws FileNotFoundException, UnsupportedEncodingException
	{
		String bestMove="",samePlayer="X",opponentPlayer="O",overallBM="",a="";
		int x=0,y = 0;
		Map<Integer,String> map=new HashMap<Integer,String>();
		int bestVal=Integer.MIN_VALUE;
		int moveVal=Integer.MIN_VALUE;
		
		PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
		
		
		for(int i=0;i<arr.length;i++)
		{
			for(int j=0;j<arr[i].length;j++)
			{
				boolean top=false,left=false,bottom=false,right=false;
				String topStr="",leftStr="",bottomStr="",rightStr="";
				//If cell is empty
				if(!arr[i][j].contains(","))
				{
					//make the move
					arr[i][j]=samePlayer+","+arr[i][j];
					bestMove="Stake";
					//check for top
					if(coordIsValid(i-1, j))
					{
						if(arr[i-1][j].contains(opponentPlayer))
						{
							if(coordIsValid(i,j-1)&&arr[i][j-1].contains(samePlayer)||
									coordIsValid(i+1, j)&&arr[i+1][j].contains(samePlayer)||
									coordIsValid(i, j+1)&&arr[i][j+1].contains(samePlayer))
							{
								topStr=arr[i-1][j];
								arr[i-1][j]=samePlayer+","+arr[i-1][j].split(",")[1];
								bestMove="Raid";
								top=true;
							}
						}
					}
					
					//check for left
					if(coordIsValid(i, j-1))
					{
						if(arr[i][j-1].contains(opponentPlayer))
						{
							if(coordIsValid(i-1,j)&&arr[i-1][j].contains(samePlayer)||
									coordIsValid(i+1, j)&&arr[i+1][j].contains(samePlayer)||
									coordIsValid(i, j+1)&&arr[i][j+1].contains(samePlayer))
							{
							leftStr=arr[i][j-1];	
							arr[i][j-1]=samePlayer+","+arr[i][j-1].split(",")[1];
							bestMove="Raid";
							left=true;
							}
						}
					
					}
					//check for bottom
					if(coordIsValid(i+1, j))
					{
						if(arr[i+1][j].contains(opponentPlayer))
						{
							if(coordIsValid(i,j-1)&&arr[i][j-1].contains(samePlayer)||
									coordIsValid(i-1, j)&&arr[i-1][j].contains(samePlayer)||
									coordIsValid(i, j+1)&&arr[i][j+1].contains(samePlayer))
							{
								bottomStr=arr[i+1][j];
							arr[i+1][j]=samePlayer+","+arr[i+1][j].split(",")[1];
							bestMove="Raid";
							bottom=true;
							}
						}
					
					}
					//check for right
					if(coordIsValid(i, j+1))
					{
						if(arr[i][j+1].contains(opponentPlayer))
						{
							if(coordIsValid(i,j-1)&&arr[i][j-1].contains(samePlayer)||
									coordIsValid(i+1, j)&&arr[i+1][j].contains(samePlayer)||
									coordIsValid(i-1, j)&&arr[i-1][j].contains(samePlayer))
							{
								rightStr=arr[i][j+1];
							arr[i][j+1]=samePlayer+","+arr[i][j+1].split(",")[1];
							bestMove="Raid";
							right=true;
							}
						}
					}
					
					if(depth==1)
					{
						moveVal=miniMaxAlphaBeta(arr,1,samePlayer,depth,Integer.MIN_VALUE,Integer.MAX_VALUE);
					}
					else
					{
						moveVal=miniMaxAlphaBeta(arr,1,opponentPlayer,depth,Integer.MIN_VALUE,Integer.MAX_VALUE);
					}
					if(moveVal>=bestVal)
					{
						if(!map.containsKey(moveVal))
						{
							bestVal=moveVal;
							x=i;
							y=j;
							overallBM=bestMove;
							map.put(bestVal, overallBM);
						}
						else
						{
							if(map.get(moveVal).contentEquals("Raid")&&bestMove.contentEquals("Stake"))
							{
								bestVal=moveVal;
								x=i;
								y=j;
								overallBM=bestMove;
								map.put(bestVal, "Stake");
							}
						}
					}
					arr[i][j]=arr[i][j].split(",")[1];
					if(top)
					{
						arr[i-1][j]=topStr;
					}
					if(left)
					{
						arr[i][j-1]=leftStr;
					}
					if(bottom)
					{
						arr[i+1][j]=bottomStr;
					}
					if(right)
					{
						arr[i][j+1]=rightStr;
					}
				}
			}
		}
		Character y1=(char)(y+65);
		String y2=y1.toString();
		System.out.println(y1+"->"+(x+1)+overallBM);
		if(overallBM.contentEquals("Stake")&&playerFlag)
		{
			for(int i=0;i<arr.length;i++)
			{
				for(int j=0;j<arr[i].length;j++)
				{
					if(arr[i][j].contains("X"))
					{
						a=a+"O";
					}
					else if(arr[i][j].contains("O"))
					{
						a=a+"X";
					}
					else
					{
						if(i==x&&j==y)
						{
							a=a+"O";
						}
						else
						{
							a=a+".";
						}
					}
				}
				a=a+"\n";
			}
		}
		else if(overallBM.contains("Stake")&&!playerFlag)
		{
			for(int i=0;i<arr.length;i++)
			{
				for(int j=0;j<arr[i].length;j++)
				{
					if(arr[i][j].contains("X"))
					{
						a=a+"X";
					}
					else if(arr[i][j].contains("O"))
					{
						a=a+"O";
					}
					else
					{
						if(i==x&&j==y)
						{
							a=a+"X";
						}
						else
						{
							a=a+".";
						}
					}
				}
				a=a+"\n";
			}
		}
		else if(overallBM.contains("Raid")&&playerFlag)
		{
			//First populate the board
			for(int i=0;i<arr.length;i++)
			{
				for(int j=0;j<arr[i].length;j++)
				{
					if(i==x&&j==y)
					{
						//check for top
						if(coordIsValid(i-1, j)&&arr[i-1][j].contains(","))
							arr[i-1][j]="X";
						//check for left
						if(coordIsValid(i, j-1)&&arr[i][j-1].contains(","))
							arr[i][j-1]="X";
						//check for bottom
						if(coordIsValid(i+1, j)&&arr[i+1][j].contains(","))
							arr[i+1][j]="X";
						//check for right
						if(coordIsValid(i, j+1)&&arr[i][j+1].contains(","))
							arr[i][j+1]="X";
					}
				}
			}	
				for(int i=0;i<arr.length;i++)
				{
					for(int j=0;j<arr[i].length;j++)
					{
						if(arr[i][j].contains("X"))
						{
							a=a+"O";
						}
						else if(arr[i][j].contains("O"))
						{
							a=a+"X";
						}
						else
						{
							if(i==x&&j==y)
							{
								a=a+"O";
							}
							else
							{
								a=a+".";
							}
						}
					}
					a=a+"\n";
				}
				
		}
		else if(overallBM.contains("Raid")&&!playerFlag)
		{
			//First populate the board
			for(int i=0;i<arr.length;i++)
			{
				for(int j=0;j<arr[i].length;j++)
				{
					if(i==x&&j==y)
					{
						//check for top
						if(coordIsValid(i-1, j)&&arr[i-1][j].contains(","))
							arr[i-1][j]="X";
						//check for left
						if(coordIsValid(i, j-1)&&arr[i][j-1].contains(","))
							arr[i][j-1]="X";
						//check for bottom
						if(coordIsValid(i+1, j)&&arr[i+1][j].contains(","))
							arr[i+1][j]="X";
						//check for right
						if(coordIsValid(i, j+1)&&arr[i][j+1].contains(","))
							arr[i][j+1]="X";
					}
				}
			}	
				for(int i=0;i<arr.length;i++)
				{
					for(int j=0;j<arr[i].length;j++)
					{
						if(arr[i][j].contains("X"))
						{
							a=a+"X";
						}
						else if(arr[i][j].contains("O"))
						{
							a=a+"O";
						}
						else
						{
							if(i==x&&j==y)
							{
								a=a+"X";
							}
							else
							{
								a=a+".";
							}
						}
					}
					a=a+"\n";
				}
				
		}
		a=a.substring(0, a.length()-1);
		writer.write(y2+(x+1)+" "+overallBM+"\n");
		writer.write(a);
		writer.close();
		
		
	}
	
	public static int miniMaxAlphaBeta(String[][] arr,int currDepth,String player,int depth,int alpha,int beta)
	{
	
		if(currDepth==depth||isBoardFull(arr))
		{
			return evaluate(arr,player);
		}
		//move for maximizer
		if(player.contentEquals("X"))
		{
			String samePlayer="X",opponentPlayer="O";
			int best=Integer.MIN_VALUE;
			for(int i=0;i<arr.length;i++)
			{
				for(int j=0;j<arr[i].length;j++)
				{
					boolean top=false,left=false,bottom=false,right=false;
					String topStr="",leftStr="",bottomStr="",rightStr="";
					//If cell is empty
					if(!(arr[i][j].contains(",")))
					{
						//make the move
						arr[i][j]=samePlayer+","+arr[i][j];
						//check for top
						if(coordIsValid(i-1, j))
						{
							if(arr[i-1][j].contains(opponentPlayer))
							{
								if(coordIsValid(i,j-1)&&arr[i][j-1].contains(samePlayer)||
										coordIsValid(i+1, j)&&arr[i+1][j].contains(samePlayer)||
										coordIsValid(i, j+1)&&arr[i][j+1].contains(samePlayer))
								{
									
									topStr=arr[i-1][j];
									arr[i-1][j]=samePlayer+","+arr[i-1][j].split(",")[1];
									top=true;
								}
							}
						}

						//check for left
						if(coordIsValid(i, j-1))
						{
							if(arr[i][j-1].contains(opponentPlayer))
							{
								if(coordIsValid(i-1,j)&&arr[i-1][j].contains(samePlayer)||
										coordIsValid(i+1, j)&&arr[i+1][j].contains(samePlayer)||
										coordIsValid(i, j+1)&&arr[i][j+1].contains(samePlayer))
								{
									
									leftStr=arr[i][j-1];
									arr[i][j-1]=samePlayer+","+arr[i][j-1].split(",")[1];
									left=true;
								}
							}

						}
						//check for bottom
						if(coordIsValid(i+1, j))
						{
							if(arr[i+1][j].contains(opponentPlayer))
							{
								if(coordIsValid(i,j-1)&&arr[i][j-1].contains(samePlayer)||
										coordIsValid(i-1, j)&&arr[i-1][j].contains(samePlayer)||
										coordIsValid(i, j+1)&&arr[i][j+1].contains(samePlayer))
								{
									bottomStr=arr[i+1][j];
									arr[i+1][j]=samePlayer+","+arr[i+1][j].split(",")[1];
									bottom=true;
								}
							}

						}
						//check for right
						if(coordIsValid(i, j+1))
						{
							if(arr[i][j+1].contains(opponentPlayer))
							{
								if(coordIsValid(i,j-1)&&arr[i][j-1].contains(samePlayer)||
										coordIsValid(i+1, j)&&arr[i+1][j].contains(samePlayer)||
										coordIsValid(i-1, j)&&arr[i-1][j].contains(samePlayer))
								{
									rightStr=arr[i][j+1];
									arr[i][j+1]=samePlayer+","+arr[i][j+1].split(",")[1];
									right=true;
								}
							}
						}
						if(currDepth+1==depth)
						{
							best=Math.max(best, evaluate(arr,samePlayer));
						}
						else
							best=Math.max(best, miniMaxAlphaBeta(arr,currDepth+1,opponentPlayer,depth,alpha,beta));
						
						alpha=Math.max(alpha, best);
						
						
						//undo the move
						arr[i][j]=arr[i][j].split(",")[1];
						if(top)
						{
							arr[i-1][j]=topStr;
						}
						if(left)
						{
							arr[i][j-1]=leftStr;
						}
						if(bottom)
						{
							arr[i+1][j]=bottomStr;
						}
						if(right)
						{
							arr[i][j+1]=rightStr;
						}
						if(beta<=alpha)
							break;
					}
				}	
			}
			return best;
		}
		else//move for minimizer
		{
			String samePlayer="O",opponentPlayer="X";
			int best=Integer.MAX_VALUE;
			for(int i=0;i<arr.length;i++)
			{
				for(int j=0;j<arr[i].length;j++)
				{
					boolean top=false,left=false,bottom=false,right=false;
					String topStr="",leftStr="",bottomStr="",rightStr="";
					
					//If cell is empty
					if(!(arr[i][j].contains(",")))
					{
						//make the move
						arr[i][j]=samePlayer+","+arr[i][j];
						//check for top
						if(coordIsValid(i-1, j))
						{
							if(arr[i-1][j].contains(opponentPlayer))
							{
								if(coordIsValid(i,j-1)&&arr[i][j-1].contains(samePlayer)||
										coordIsValid(i+1, j)&&arr[i+1][j].contains(samePlayer)||
										coordIsValid(i, j+1)&&arr[i][j+1].contains(samePlayer))
								{
									topStr=arr[i-1][j];
									arr[i-1][j]=samePlayer+","+arr[i-1][j].split(",")[1];
									top=true;
								}
							}
						}

						//check for left
						if(coordIsValid(i, j-1))
						{
							if(arr[i][j-1].contains(opponentPlayer))
							{
								if(coordIsValid(i-1,j)&&arr[i-1][j].contains(samePlayer)||
										coordIsValid(i+1, j)&&arr[i+1][j].contains(samePlayer)||
										coordIsValid(i, j+1)&&arr[i][j+1].contains(samePlayer))
								{
									leftStr=arr[i][j-1];
									arr[i][j-1]=samePlayer+","+arr[i][j-1].split(",")[1];
									left=true;
								}
							}

						}
						//check for bottom
						if(coordIsValid(i+1, j))
						{
							if(arr[i+1][j].contains(opponentPlayer))
							{
								if(coordIsValid(i,j-1)&&arr[i][j-1].contains(samePlayer)||
										coordIsValid(i-1, j)&&arr[i-1][j].contains(samePlayer)||
										coordIsValid(i, j+1)&&arr[i][j+1].contains(samePlayer))
								{
									bottomStr=arr[i+1][j];
									arr[i+1][j]=samePlayer+","+arr[i+1][j].split(",")[1];
									bottom=true;
								}
							}

						}
						//check for right
						if(coordIsValid(i, j+1))
						{
							if(arr[i][j+1].contains(opponentPlayer))
							{
								if(coordIsValid(i,j-1)&&arr[i][j-1].contains(samePlayer)||
										coordIsValid(i+1, j)&&arr[i+1][j].contains(samePlayer)||
										coordIsValid(i-1, j)&&arr[i-1][j].contains(samePlayer))
								{
									rightStr=arr[i][j+1];
									arr[i][j+1]=samePlayer+","+arr[i][j+1].split(",")[1];
									right=true;
								}
							}
						}

						best=Math.min(best, miniMaxAlphaBeta(arr,currDepth+1,opponentPlayer,depth,alpha,beta));
						beta=Math.min(beta, best);
						
						//undo the move
						arr[i][j]=arr[i][j].split(",")[1];
						if(top)
						{
							arr[i-1][j]=topStr;
						}
						if(left)
						{
							arr[i][j-1]=leftStr;
						}
						if(bottom)
						{
							arr[i+1][j]=bottomStr;
						}
						if(right)
						{
							arr[i][j+1]=rightStr;
						}
						if(beta<=alpha)
							break;
					}
				}	
			}
			return best;
		}
		
	}

	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
	{
		Scanner sc=new Scanner(new FileReader("input.txt"));
		String fileData="";
		while(sc.hasNext())
		{
			fileData=fileData+sc.nextLine()+"\n";
		}
		sc.close();
		String file[]=fileData.split("\n");
		n=Integer.parseInt(file[0].trim());//Dimensions of board
		String mode=file[1].trim();//Mode of play-Minmax or Alphabeta
		String playerType=file[2].trim();//Player type X or O
		int depth=Integer.parseInt(file[3].trim());//Depth till which the search needs to be done
		String arr[][]=new String[n][n];//Square values
		int k=4;
		//Initialize the board with the given square values
		for(int i=0;i<arr.length;i++)
		{
			String[] numsRow=file[k].split(" ");
			for(int j=0;j<arr[i].length;j++)
			{
				arr[i][j]=numsRow[j];
			}
			k++;
		}
		boolean playerFlag=false;
		if(playerType.contentEquals("O"))
		{
			playerFlag=true;
			playerType="X";
		}
		for(int i=0;i<arr.length;i++)
		{
			String row=file[k];
			for(int j=0;j<arr[i].length;j++)
			{
				if(row.charAt(j)=='X'&&playerFlag)
				{
					arr[i][j]="O,"+arr[i][j];
				}
				else if(row.charAt(j)=='X'&&!playerFlag)
				{
					arr[i][j]="X,"+arr[i][j];
				}
				else if(row.charAt(j)=='O'&&playerFlag)
				{
					arr[i][j]="X,"+arr[i][j];
				}
				else if(row.charAt(j)=='O'&&!playerFlag)
				{
					arr[i][j]="O,"+arr[i][j];
				}
			}
			k++;
		}
		
		if(mode.contentEquals("MINIMAX"))
			minMaxBestMove(arr,depth,playerType,playerFlag);
		else if(mode.contentEquals("ALPHABETA"))
			alphaBeta(arr,depth,playerType,playerFlag);
	}
	
	public static boolean coordIsValid(int x,int y)
	{
		return x>=0&&x<n&&y>=0&&y<n;
	}
	
	public static int evaluate(String[][] arr,String player)
	{
		String samePlayer=player;
		String opponentPlayer=player.contentEquals("X")?"O":"X";
		int scoreX=0,scoreY=0;
		for(int i=0;i<arr.length;i++)
		{
			for(int j=0;j<arr[i].length;j++)
			{
				if(arr[i][j].contains(samePlayer))
				{
					scoreX=scoreX+Integer.parseInt(arr[i][j].split(",")[1]);
				}
				else if(arr[i][j].contains(opponentPlayer))
				{
					scoreY=scoreY+Integer.parseInt(arr[i][j].split(",")[1]);
				}
			}
		}
		return scoreX-scoreY;
	}
	public static boolean isBoardFull(String[][]arr)
	{
		for(int i=0;i<arr.length;i++)
		{
			for(int j=0;j<arr[i].length;j++)
			{
				if(!(arr[i][j].contains("X")||arr[i][j].contains("O")))
					return false;
			}
		}
		return true;
	}
	
}
