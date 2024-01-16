//javac --module-path "D:\java programs\FX\lib" --add-modules javafx.controls quiz.java
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Scanner;
import javafx.scene.text.Font;

public class quiz extends Application 
{
     Timeline timeline;
    int marks=0;boolean QuizStarted=false;

    public static void main(String args[])
    {
        launch();
    }

    public void start(Stage s)
    {
        File fp = new File("qui.txt");
        try
       {
            Scanner k = new Scanner(fp);
            String arr[][] = new String[5][5];

	   Label head = new Label(k.nextLine());
       	   Font font1 = new Font(18);
	   head.setFont(font1);


            Label loop = new Label();
	//setting font size to label
	   Font font = new Font(20);
           loop.setFont(font);		

	   TilePane ans1=new TilePane();
	   TilePane ans2=new TilePane();

           Button save = new Button("Save");
           Label l[] = new Label[5];

           Button st = new Button("Start");

           RadioButton r[] = new RadioButton[20];

           ToggleGroup tg[] = new ToggleGroup[5];
Button retry = new Button("Retry");
           // reading the lines from file

           for (int i = 0; i < 5; i++) {
               for (int j = 0; j < 5; j++) {
                   arr[i][j] = k.nextLine();
               }
           }
           // creating labels and RadioButtons
           int t = 0;
           for (int i = 0; i < 5; i++) 
	   {
               for (int j = 0; j < 5; j++)
	       {
                   if (j == 0)
                       l[i] = new Label(arr[i][0]);
                   else
		   {
                       r[t] = new RadioButton(arr[i][j]);
                       t++;
                    }
                }
            }
	    int p=0;
            for (int i = 0; i < 5; i++) {
                tg[i] = new ToggleGroup();
                for (int j = 0; j < 4; j++) {
                    r[p].setToggleGroup(tg[i]);p++;
                }
            }

           for(int i=0;i<20;i++)
			r[i].setDisable(true);	
		loop.setText("Start The QUIZ");
	
            EventHandler<ActionEvent> tf = new EventHandler<ActionEvent>() 
	   {
                private int remainingSeconds = 60;

                public void handle(ActionEvent e)
	        {
		    if (timeline == null)
		    {
			if(e.getSource()==st)
			{
				QuizStarted=true;
				for(int i=0;i<20;i++)
					r[i].setDisable(false);
			}
                        timeline = new Timeline(
                                new KeyFrame(Duration.seconds(1), event -> {
                                    remainingSeconds--;
                                    loop.setText("Time remaining: " + remainingSeconds + " seconds");
				    
			if (remainingSeconds <= 0) 
			{
                              timeline.stop();
                              for(int i=0;i<20;i++)
					r[i].setDisable(true);
			      save.setDisable(true);
                       	      st.setDisable(true);
                              loop.setText("Time over");
                         }
                     })
              );
                        timeline.setCycleCount(60);
                        timeline.play();
          }
		
		
		    if(e.getSource()==save)
		    {	
			for(int i=0;i<20;i++)
				r[i].setDisable(true);
			timeline.stop();
			save.setDisable(true);
                        st.setDisable(true);
			loop.setText("QUIZ Stopped at "+(60-remainingSeconds)+"seconds");
			
			if(r[1].isSelected())
			{
				marks+=5;	
			}
			if(r[6].isSelected())
			{
				marks+=5;
			}
			if(r[9].isSelected())
			{
				marks+=5;	
			}
			if(r[14].isSelected())
			{
				marks+=5;	
			}
			if(r[19].isSelected())
			{
				marks+=5;
			}
			retry.setVisible(true);
			Label total=new Label("Total Score = "+marks+"/25");
			ans1.getChildren().add(total);ans1.setAlignment(Pos.CENTER);
			Font font3 = new Font(22);
	   		total.setFont(font3);
		
			Label per=new Label("Percentage="+((marks*100)/25)+"%");
			ans2.getChildren().add(per);ans2.setAlignment(Pos.CENTER);
			Font font4 = new Font(22);
	   		per.setFont(font4);	


	            }			
	        }
            };
            // firing the events
	    if(QuizStarted==true)
	    {
              for (int i = 0; i < 20; i++)
	      {
                 r[i].setOnAction(tf);
              }
	    }
	   
            st.setOnAction(tf);
	
	    save.setOnAction(tf);		

            BorderPane w = new BorderPane(head);
	    BorderPane w1 = new BorderPane(loop);
	 
		
	   retry.setVisible(false);
	   EventHandler<ActionEvent> retryHandler = new EventHandler<ActionEvent>() {
    	public void handle(ActionEvent e) {
        marks = 0;
        QuizStarted = false;
        loop.setText("Start The QUIZ");
        for (int i = 0; i < 20; i++) {
            r[i].setSelected(false);
            r[i].setDisable(false);
        }
        save.setDisable(false);
        st.setDisable(false);
        timeline.stop();
        timeline = null;
        ans1.getChildren().clear();
        ans2.getChildren().clear();
	save.setVisible(true);
        retry.setVisible(false);
    }
};

retry.setOnAction(retryHandler);

            VBox v = new VBox(w,w1,st,l[0],r[0],r[1],r[2],r[3],l[1],r[4],r[5],r[6],r[7],l[2],r[8],r[9],r[10],r[11],l[3],
r[12],r[13],r[14],r[15],l[4],r[16],r[17],r[18],r[19],save,retry,ans1,ans2);
	
        	save.setAlignment(Pos.CENTER);    

            Scene s1 = new Scene(v);
	    s.setTitle("Quiz game");
            s.setScene(s1);
            s.setHeight(740);
            s.setWidth(800);
            s.show();
        } 
	catch (Exception h)
        {
            System.out.print(h);
        }
    }
}
          
