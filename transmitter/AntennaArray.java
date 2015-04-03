package transmitter;

import org.jtransforms.fft.DoubleFFT_1D;
import edu.mit.streamjit.api.RoundrobinJoiner;
import edu.mit.streamjit.api.RoundrobinSplitter;
import edu.mit.streamjit.api.Splitjoin;
import edu.mit.streamjit.test.apps.fft5.FFT;


public class AntennaArray extends edu.mit.streamjit.api.Pipeline<Float, Float>{
	static int fftlength=8;	
	static int stuff=2;
	
	public AntennaArray(){				
		super(new Splitjoin<Float,Float>(
				new RoundrobinSplitter<Float>(fftlength*2),
				new RoundrobinJoiner<Float>((fftlength+2*stuff)*2),
				new Antenna(),
				new Antenna()
				)
//			new Printer()
		);
	}


	private static class DFT  extends
	edu.mit.streamjit.api.Filter<Float, Float> {
		
		DoubleFFT_1D f; 
		public DFT(int length) {
			super(24,24);
			f=new DoubleFFT_1D(12);
		}

		@Override
		public void work() {
			double[] a=new double[24];
			for (int i = 0; i < 24; i++) {
				a[i]=pop();								
			}
			f.complexForward(a);
			for (int i = 0; i < 24; i++) {
				push((float)a[i]);								
			}
			
		}
		
	}
	
	
	private static class IFFTPrepare  extends
	edu.mit.streamjit.api.Filter<Float, Float> {
			
		
		public IFFTPrepare() {
			super(16, 24);
					
		}

		@Override
		public void work() {
			for (int i = 0; i < 2; i++) {
				push(0f);
				push(0f);
				
			}
			
			for (int i = 0; i < 8; i++) {
				push(pop());
				push((-1*pop()));
				
			}
			
			for (int i = 0; i < 2; i++) {
				push(0f);
				push(0f);
				
			}

		}
	}
	
	private static class Antenna  extends
	edu.mit.streamjit.api.Pipeline<Float, Float> {
		
		public Antenna() {
			super(new FFT(8),new IFFTPrepare(),new DFT(12),new ScaleConjugate(),new CP());
		}

		
	}
	
	private static class ScaleConjugate  extends
	edu.mit.streamjit.api.Filter<Float, Float> {
		
		public ScaleConjugate() {
			super(2,2);
		}

		@Override
		public void work() {
			push(pop()/(12));
			push(-1*pop()/(12));

		}
	}
	
	private static class CP  extends
	edu.mit.streamjit.api.Filter<Float, Float> {
		
		public CP() {
			super(1,1);
		}

		@Override
		public void work() {
			push(pop());

		}
	}
	
	private static class Printer extends
	edu.mit.streamjit.api.Filter<Float, Float> {
		
		public Printer() {
			super(1, 1);
		}

		@Override
		public void work() {
			float a = pop();
			System.out.println(a);
			push(a);

		}
	}
	
}
