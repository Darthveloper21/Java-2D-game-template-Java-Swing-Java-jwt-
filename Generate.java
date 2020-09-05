import java.util.*;
import java.io.*;

public class Generate
{
    public static int[][] initmap() throws IOException
    {
        File mymap = new File("Map/map_init.txt");
        Scanner input = new Scanner(mymap);
        
        int[][] grid = new int[205][205];
        for(int i = 0; i < 13; ++i)
            for(int j = 0; j < 17; ++j)
                grid[i][j] = input.nextInt();
        input.close();
        return grid;
    }

    public static int[][] nextgen(int[][] grid)
	{
		int[][] future = new int[205][205];
       
        for(int i = 0; i < 13; ++i)
    	{
        	for(int j = 0; j < 17; ++j)
        	{
            	int alive = 0;
            	for(int q = -1; q <= 1; ++q)
                	for(int k = -1; k <= 1; ++k)
                	{
                    	if(i + q >= 0 && j + k >= 0 && i + q < 13 && j + k < 17)
                        	alive+=grid[i + q][j + k];
                	}
            	alive-=grid[i][j];

            	if(grid[i][j] == 1 && alive < 2) future[i][j] = 0;
            	else if(grid[i][j] == 1 && alive > 3) future[i][j] = 0;
            	else if(grid[i][j] == 0 && alive == 3) future[i][j] = 1;
            	else future[i][j] = grid[i][j];
        	}
    	}

    	return future;
	}
}
