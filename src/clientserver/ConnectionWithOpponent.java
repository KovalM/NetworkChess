package clientserver;

import chessmodel.CheckerboardPosition;
import chessview.DeskView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionWithOpponent {
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private int passLeft;
    private boolean endMove;
    private DeskView deskView;

    public ConnectionWithOpponent(Socket socket, DeskView deskView){
        this.deskView = deskView;
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void sendMove(CheckerboardPosition start, CheckerboardPosition finish){
        passLeft--;
        try {
            outputStream.writeObject(start);
            outputStream.writeObject(finish);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (passLeft==0){
            endMove = true;
        }
    }

    public void sendTypeMove(String typeMove){
        passLeft = typeMove.equals("simple")?1:2;
        try {
            outputStream.writeObject(typeMove);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEnd(){
        try {
            outputStream.writeObject("end");
        } catch (IOException e) {
            e.printStackTrace();
        }
        waitMove();
    }
    public void waitMove(){
        deskView.getTimerView().end();
        String typeMove, end;
        CheckerboardPosition start, finish;
        while (true) {
            try {
                typeMove = (String) inputStream.readObject();
                if (typeMove.equals("simple")){
                    start = (CheckerboardPosition)inputStream.readObject();
                    finish = (CheckerboardPosition)inputStream.readObject();
                    end = (String)inputStream.readObject();
                    deskView.movePieceOnDesk(deskView.getPieceViewOnDesk(start), finish);
                    break;
                } else if (typeMove.equals("castling")){
                    start = (CheckerboardPosition)inputStream.readObject();
                    finish = (CheckerboardPosition)inputStream.readObject();
                    deskView.movePieceOnDesk(deskView.getPieceViewOnDesk(start), finish);
                    start = (CheckerboardPosition)inputStream.readObject();
                    finish = (CheckerboardPosition)inputStream.readObject();
                    end = (String)inputStream.readObject();
                    deskView.movePieceOnDesk(deskView.getPieceViewOnDesk(start), finish);
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        deskView.getTimerView().begin();
        endMove = false;
        deskView.displayStartMove();
    }

    public boolean isEndMove() {
        return endMove;
    }
}
