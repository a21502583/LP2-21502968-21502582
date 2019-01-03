package pt.ulusofona.lp2.crazyChess;

import java.util.List;

public class PoneiMagico extends CrazyPiece {

    public PoneiMagico(int idPeca, int idTipoPeca, int idEquipa, String alcunha, int x, int y) {
        super(idPeca, idTipoPeca, idEquipa, alcunha, x, y);
    }

    public PoneiMagico(String idPeca, String idTipoPeca, String idEquipa, String alcunha, int x, int y) {
        super(idPeca, idTipoPeca, idEquipa, alcunha, x, y);
    }

    @Override
    public String getImagePNG() {
        if (idEquipa == 10){
            return "HorseBlack.png";
        } else {
            return "HorseWhite.png";
        }
    }

    @Override
    public boolean movimento(int xO, int yO, int xD, int yD, CrazyPiece crazy) {
        int deltaX = Math.abs(xD - xO);
        int deltaY = Math.abs(yD - yO);

        int direcaoX = xD - xO;
        int direcaoY = yD - yO;

        if (deltaX != 2) {
            return false;
        }
        if (deltaY != 2) {
            return false;
        }

        if(!findFriend(xD, yD, idPeca, idEquipa)){
            return false;
        }

        if(!antiRei(xO, yO, xD, yD, direcaoX, direcaoY)){
            return false;
        }

        findCapture(xD, yD, idPeca, idEquipa);
        coordenadaX = xD;
        coordenadaY = yD;

        return true;//TODO:Testado e encontra reis
    }

    public boolean antiRei(int xO, int yO, int xD, int yD, int direcaoX, int direcaoY) {
        List<CrazyPiece> reis = getRei();
        int deltaXReiOrigem;
        int deltaYReiOrigem;

        int deltaXReiDestino;
        int deltaYReiDestino;

        for (CrazyPiece rei: reis){
            deltaXReiOrigem = Math.abs(rei.coordenadaX - xO);
            deltaYReiOrigem = Math.abs(rei.coordenadaY - yO);

            deltaXReiDestino = Math.abs(rei.coordenadaX - xD);
            deltaYReiDestino = Math.abs(rei.coordenadaY - yD);

            if (rei.coordenadaX == xD && rei.coordenadaY == yD){
                return true;
            }

            if (deltaXReiOrigem > 2 || deltaYReiOrigem > 2) {
                continue;
            }

            if (deltaXReiOrigem == 1 && deltaYReiOrigem == 1){
                continue;
            }

            if (direcaoX < 0 && direcaoY > 0) {//ESQUERDA BAIXO

                if (deltaXReiOrigem == -1 && deltaYReiOrigem == 0 || deltaXReiOrigem == 0 && deltaYReiOrigem == 1){
                    return false;
                }

                if(deltaXReiDestino <= 2 && deltaYReiDestino <= 2){
                    return false;
                }

            }

            if (direcaoX < 0 && direcaoY < 0) {//ESQUERDA CIMA

                if (deltaXReiOrigem == -1 && deltaYReiOrigem == 0 || deltaXReiOrigem == 0 && deltaYReiOrigem == -1){
                    return false;
                }

                if(deltaXReiDestino <= 2 && deltaYReiDestino <= 2){
                    return false;
                }
            }

            if (direcaoX > 0 && direcaoY < 0) {//DIREITA CIMA

                if (deltaXReiOrigem == 1 && deltaYReiOrigem == 0 || deltaXReiOrigem == 0 && deltaYReiOrigem == -1){
                    return false;
                }

                if(deltaXReiDestino <= 2 && deltaYReiDestino <= 2){
                    return false;
                }
            }

            if (direcaoX > 0 && direcaoY > 0) {//DIREITA BAIXO

                if (deltaXReiOrigem == 1 && deltaYReiOrigem == 0 || deltaXReiOrigem == 0 && deltaYReiOrigem == 1){
                    return false;
                }

                if(deltaXReiDestino <= 2 && deltaYReiDestino <= 2){
                    return false;
                }
            }

        }

        return true;
    }
    
     @Override
    public boolean movimentoPrevisao(int xO, int yO, int xD, int yD, CrazyPiece crazy) {
        return false;
    }

}
