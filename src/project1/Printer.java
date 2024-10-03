package project1;

public class Printer {

	public class DiamondPrinter extends Thread {
	    private int max; 
	    private long delay; 

	    public DiamondPrinter(int max, long delay) {
	        this.max = max;
	        this.delay = delay;
	    }

	    @Override
	    public void run() {
	        int mid = max / 2;
	        int k = 0;
	        for (int i = 0; i < max; i++) {
	            if (i <= mid) {
	                printSpaces(i);
	                printStars((max - (2 * i)));
	            } else {
	                printSpaces(max - i - 1);
	                printStars(3 + k * 2);
	                k++;
	            }
	            try {
	                Thread.sleep(delay); 
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    private void printSpaces(int count) {
	        for (int i = 0; i < count; i++) {
	            System.out.print("  ");
	        }
	    }

	    private void printStars(int count) {
	        for (int i = 0; i < count; i++) {
	            System.out.print("☎");
	        }
	        System.out.println(); 
	    }
	}
	public class CharacterPrinter extends Thread {
	    private String text; 
	    private long delay; 

	    public CharacterPrinter(String text, long delay) {
	        this.text = text;
	        this.delay = delay;
	    }

	    @Override
	    public void run() {
	        for (int i = 0; i < text.length(); i++) {
	            System.out.print(text.charAt(i));
	            try {
	                Thread.sleep(delay);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}
	
}
