package BackEnd;

public class Equations {
	public void Handler(ComplexNumber M[][], int m, int n)
	{
	    ComplexNumber h = new ComplexNumber(0), tmp = new ComplexNumber(0);
	    int i, j, k,l ,flag, line = 0;
	    for (i = 0; i < n-1; i++)
	    {
	        if (M[i][i].equals(0))
	        {
	            for (k = i+1; k < m; k++)
	            {
	                if (!M[k][i].equals(0))
	                {
	                    for (l = 0; l < n; l++)
	                    {
	                        tmp.setValue(M[i][l]);
	                        M[i][l].setValue(M[k][l]);
	                        M[k][l].setValue(tmp);
	                    }

	                    break;
	                }
	            }
	        }
	        for (j = 0; j < m; j++)
	        {
	            int group = 2;
	            if ( (j != i) && (!M[j][i].equals(0)) )
	            {
	                group = 0;
	            }

	            if ( (j == i) && (!M[j][i].equals(1)) )
	            {
	                group = 1;
	            }

	            switch (group)
	            {
	                case 0:
	                    for (k = 0; k < m; k++) 
	                    {
	                        if (k != j)
	                        {
	                            h.setValue(0);
	                            flag = 0;
	                            for (l = 0; l < i; l++) if (!M[k][l].equals(0)) flag=1;
	                            if ((flag == 0) && (!M[k][i].equals(0)))
	                            {
	                                line = k;
	                                h.setValue((M[j][i].mul(-1)).div(M[line][i]));
	                                break;
	                            }
	                        }
	                    }
	                    if (!h.equals(0))
	                    {
	                        for (k = 0; k < n; k++)
	                        {
	                            M[j][k].setValue(M[j][k].add(h.mul(M[line][k])));
	                        }
	                    }

	                    break;

	                case 1:
	                    flag = 0;
	                    for (l = 0; l < i; l++)
	                    {
	                        if (!M[j][l].equals(0))
	                        {
	                            flag = 1;
	                        }
	                    }

	                    h.setValue(M[j][i]);
	                    if ( (!h.equals(0)) && (flag == 0))
	                    {
	                        for (l = 0; l < n; l++)
	                        {
	                            M[j][l].setValue(M[j][l].div(h));
	                        }
	                    }
	                    
	                    break;
	            }
	        }
	    }
	}
	
	public void Result(ComplexNumber M[][], int m, int n, ComplexNumber result)
	{
	    int i,j;

	    int rA = 0;
	    int uprankA;
	    for (i = 0; i < m; i++)
	    {
	        
	        uprankA = 0;
	        for (j = 0; j < n - 1; j++)
	        {
	            
	            if (!M[i][j].equals(0))
	            {
	                uprankA = 1;
	                break;
	            }                
	        }

	        rA += uprankA;
	    }

	    
	    int rM = 0;
	    int uprankM;
	    for (i = 0; i < m; i++)
	    {
	        uprankM = 0;
	        for (j = 0; j < n; j++)
	        {
	             if ( !M[i][j].equals(0))
	            {
	                uprankM = 1;
	                break;
	            }                
	        }
	        rM += uprankM;
	    }

	    if (rA == rM && rA == n - 1)
	    {
	    	result.setValue(M[0][n-1]);
	    }

	} 
}
