package BowlingScore;

public class BowlingScore{
	private int score;
	private String[] line = new String[21];
	private String[][] frames = new String[9][2];
	private String[] frame10 = new String[3];
	private int strikeCounter = 0;

	//constructor
	public BowlingScore(String[] l){
		line = l;
		splitLine(line);
	}

	//Goes frame by frame to add in the correct score
	private int score(){
		//tenth frame is special so we need to do special test for it
		for(int currFrame = 0; currFrame < 9; currFrame++){
			//if its a strike then we must use the strike adder
			if(frames[currFrame][0].equals("X")){
				score = score + 10 + strikeAdder(currFrame);
			}
			else if(frames[currFrame][1].equals("/")){
				score = score + 10 + spareAdder(currFrame);
			}
			//test special cases for each frame
			else if(!frames[currFrame][0].equals("-")){
				int x = Integer.parseInt(frames[currFrame][0]);
				score += x;
				if(!frames[currFrame][1].equals("-")){
					int y = Integer.parseInt(frames[currFrame][1]);
					score += y;
				}
			}
			/* TODO: The code below is duplicated above. Is there a way to condense these? */
			else if(!frames[currFrame][1].equals("-")){
				int x = Integer.parseInt(frames[currFrame][1]);
				score += x;
			}
		}
		//case for if the first roll in frame 10 is a strike
		tenthFrame();
		return score;
	}

	private void tenthFrame(){
		if(frame10[0].equals("X")){
			score += 10;
			//second roll is a strike
			if(frame10[1].equals("X")){
				score += 10;
				//third roll is a strike
				if(frame10[2].equals("X")){
					score += 10;
				}
				else if(!frame10[2].equals("-")){
					int x = Integer.parseInt(frame10[2]);
					score += x;
				}
			}
			//case first roll is a spare
			else if(frame10[2].equals("/")){
				score += 10;
			}
			//first roll is a gutter ball 
			else if(!frame10[1].equals("-")){
				int x = Integer.parseInt(frame10[1]);
				score += x;
				if(!frame10[2].equals("-")){
					int y = Integer.parseInt(frame10[2]);
					score += y;
				}
			}
			else{
				if(!frame10[2].equals("-")){
					int x = Integer.parseInt(frame10[2]);
					score += x;
				}
			}
		}
		else if(frame10[1].equals("/")){
			score += 10;
			if(frame10[2].equals("X")){
				score += 10;
			}
			else if(!frame10[2].equals("-")){
				int x = Integer.parseInt(frame10[2]);
				score += x;
			}
		}
		else if(!frame10[0].equals("-")){
			int x = Integer.parseInt(frame10[0]);
			score += x;
			if(!frame10[1].equals("-")){
				int y = Integer.parseInt(frame10[1]);
				score += y;
			}
		}
		else{
			if(!frame10[1].equals("-")){
				int x = Integer.parseInt(frame10[1]);
				score += x;
			}
		}
	}

	//breaks the line into the frames
	private void splitLine(String[] l){
		for(int i = 0; i < l.length; i++){
			if(l[i] != null){
				int frame = whatFrame(i);
				int temp = (i + strikeCounter) % 2; //finds what spot in the frame its in

				//puts the score into the correct frame and spot in the frame
				if(frame >= 10){
					if(frame10[0] == null)
						frame10[0] = l[i];
					else if(frame10[1] == null)
						frame10[1] = l[i];
					else
						frame10[2] = l[i];
				}
				else{
					frames[frame-1][temp] = l[i];
				}

				//we need to move up a frame if there is a strike
				if(l[i].equals("X"))
					strikeCounter++;
			}
		}
	}

	//adds the next to scores for a strike
	private int strikeAdder(int frame){
		int add = 0;

		// checks special case if its in the ninth frame and a strike we need to stay in that frame
		if(frame == 8 && frame10[0].equals("X")){
			add += 10;
			if(frame10[1].equals("X"))
				add+=10;
			else if(!frame10[1].equals("-")){
				int x = Integer.parseInt(frame10[1]);
				add += x;
			}
		}
		else if(frame == 7 && frames[frame+1][0].equals("X")){
			add += 10;
			if(frame10[0].equals("X"))
				add += 10;
			else if(!frame10[0].equals("-")){
				int x = Integer.parseInt(frame10[0]);
				add += x;
			}
		}
		//checks to see if the first score is strike so it can jump to the next frame if it is
		else if(frames[frame+1][0].equals("X")){
			add += 10;
			if(frames[frame+2][0].equals("X"))
				add += 10;
			else if(frames[frame+2][0].equals("-"))
				add += 0;
			else{
				int x = Integer.parseInt(frames[frame+2][0]);
				add += x;
			}
		}
		//if the next frame is a spare just add ten
		else if(frames[frame+1][1].equals("/"))
			add = 10;
		//if they are both gutter balls add nothing
		else if(!frames[frame+1][0].equals("-") && !frames[frame+1][1].equals("-")){
			int x = Integer.parseInt(frames[frame+1][0]);
			int y = Integer.parseInt(frames[frame+1][1]);
			add = add + x + y;
		}
		//if just the first one is not a gutter ball then just add the first
		else if(!frames[frame+1][0].equals("-")){
			int x = Integer.parseInt(frames[frame+1][0]);
			add = x;
		}
		//if just the second one is not a gutter ball than just add the second
		else if(!frames[frame+1][1].equals("-")){
			int x = Integer.parseInt(frames[frame+1][1]);
			add = x;
		}
		return add;
	}

	//adds the next score to a spare
	private int spareAdder(int frame){
		int add = 0;
		if(frame == 8){
			if(frame10[0].equals("X"))
				add = 10;
			else if(!frame10.equals("-")){
				int x = Integer.parseInt(frame10[0]);
				add = x;
			}
		}
		else if(frames[frame+1][0].equals("X"))
			add = 10;
		else if(!frames[frame+1][0].equals("-")){
			int x = Integer.parseInt(frames[frame+1][0]);
			add = x;
		}
		return add;
	}


	//Figures out what frame I am in
	private int whatFrame(int x){
		int frame;
		if(x != 20){
			frame = ((x +strikeCounter)/2) + 1;
		}
		else{
			frame = 10;
		}
		return frame;
	}

	public static void main(String[] args){
		String[] score300 = {"X","X","X","X","X","X","X","X","X","X","X","X"};
		BowlingScore xxx = new BowlingScore(score300);
		System.out.println(xxx.score());

		String[] score90 = {"9","-","9","-","9","-","9","-","9","-","9","-","9","-","9","-","9","-","9","-"};
		BowlingScore x90 = new BowlingScore(score90);
		System.out.println(x90.score());

		String[] score150 = {"5","/","5","/","5","/","5","/","5","/","5","/","5","/","5","/","5","/","5","/","5"};
		BowlingScore x150 = new BowlingScore(score150);
		System.out.println(x150.score());

		String[] score167 = {"X","7","/","9","-","X","-","8","8","/","-","6","X","X","X","8","1"};
		BowlingScore x167 = new BowlingScore(score167);
		System.out.println(x167.score());
	}
}
