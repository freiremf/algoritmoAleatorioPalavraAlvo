import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Comparator;

public class Selection {
	// Função para criar números aleatórios de 0 a 10 em cada vetor
	public static void criaMatrizAleatoria(double matrix[][])
	{
		MersenneTwisterFast obj = new MersenneTwisterFast();
		
		for(int i=0; i<matrix.length; i++)
			for(int j = 0; j < matrix[i].length; j++)
				matrix[i][j] = obj.nextInt(11)*.1;
	}
	//Função para inicializar a matriz
	public static void inicializaMatriz(double matrix[][])
	{
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++)
				matrix[i][j] = 0;	
	}
	// Função para calcular o Fitness
	public static void calculaFitness(double matrixPop[][], double matrixFitness[][])
	{
		//maximização da função		
		for(int i=0; i<matrixPop.length; i++)
		{
			for(int j=0; j<matrixPop[i].length; j++)
			{
				matrixFitness[i][1] = i;
				matrixFitness[i][0] = matrixFitness[i][0] + (Math.abs(matrixPop[i][j] - 0.6));				
			}
			//tratamento para divisão por 0
			if(matrixFitness[i][0]!=0)
				matrixFitness[i][0] = 1/matrixFitness[i][0];
			else
				matrixFitness[i][0] = 10;
		}
	}
	//Ordenação	
	public static void ordenaFitness(double matrixFitness[][]) 
	{	
		Arrays.sort(matrixFitness, new Comparator<double[]>() {
			public int compare(double[] o1, double[] o2) {
				return Double.compare(o2[0], o1[0]);
				}  
			});    
    }
	//Função para substituir os 30 piores indivíduos
	public static void mataTrinta(double matrixPop[][], double matrixFitness[][])
	{
		MersenneTwisterFast obj = new MersenneTwisterFast();
		for (int i = 30; i < matrixFitness.length; i++)
		{
			for (int j = 0; j < matrixPop[i].length; j++) {
				int aux = (int)matrixFitness[i][1];
				matrixPop[aux][j] = obj.nextInt(11)*.1;
			}
		}
	}
	//Main
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		
		double [][] matrizPopulacao = new double [60][10];
		double [][] fitness = new double [60][2];
		
		PrintWriter writer = new PrintWriter("Fitness.txt", "UTF-8");
		
		criaMatrizAleatoria(matrizPopulacao);
		inicializaMatriz(fitness);
		
		for (int i = 0; i < 10000; i++) 
		{				
			calculaFitness(matrizPopulacao, fitness);
			ordenaFitness(fitness);
			
			double media = 0;
			
			for (int j = 0; j < fitness.length; j++)
				media = media + fitness[j][0];
			
			media = media/fitness.length;
				
			writer.println(fitness[0][0] + "," + media);
			
			if(fitness[0][0] == 10)
			{
				System.out.println("Convergiu!");
				break;
			}
			mataTrinta(matrizPopulacao, fitness);
			inicializaMatriz(fitness);
		}
		writer.close();
	}
}

