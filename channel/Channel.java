package channel;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.ejml.simple.SimpleMatrix;

import edu.mit.streamjit.utilities.Utilities;

public class Channel extends
edu.mit.streamjit.api.Filter<Float, Float>{
	
	double tx_corr_coeff = 0.3f;	//Medium
    double rx_corr_coeff = 0.9f;
    
    int numSubcarriers=12;
    double f_carr=1400000;
    
    int[] path_delays = {0, 30,70, 90, 110, 190, 410}; //EPA 5Hz
    float[] path_gains = {0, -1, -2, -3, -8, -17.2f, -20.8f};
    int dopp_freq = 5;
    int no_taps = 7;
    
    double[][] tx_corr_matrix = {{1, tx_corr_coeff},{tx_corr_coeff, 1}};
    double[][] rx_corr_matrix = {{1, rx_corr_coeff},{rx_corr_coeff, 1}};
    SimpleMatrix corr_matrix = (new SimpleMatrix(tx_corr_matrix)).kron(new SimpleMatrix(rx_corr_matrix));    
    SimpleMatrix sqrt_corr_matrix = Utilities.sqrtm(corr_matrix);
    
    int l= numSubcarriers;
    double[] f=new double[2*l];
    
    {
	    for(int k=0 ; k<l ; k++){
	    	f[k]=f_carr-59*15*Math.pow(10,-6)+15*Math.pow(10,-6)*k;
	        f[l+k]=f_carr-59*15*Math.pow(10,-6)+15*Math.pow(10,-6)*k;    
	    }	
    }  
    
    double[][] Hr=new double[2*l][2*l];
    double[][] Hi=new double[2*l][2*l];
    {
	    for(int k=0 ; k<2*l ; k++){
	    	for(int j=0 ; j<2*l ; j++){
		    	Hr[k][j]=0;
		    	Hi[k][j]=0;
		    }
	    }
    }
    
    {
	    for(int k=0 ; k<l ; k++){
	    	
	    	
	    	
	    }	
    }
       
    	    
    	    
    
    
	public Channel(int length) {
		super(length*2,length*2);
		
	}

	@Override
	public void work() {
		
			push(pop());
			push(pop());
			
		
	}

}
