//GRESSIER Gabriel 2A

package sujet3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

class Camion {
    static final int nbColis = 100;

    int id; // identifiant du camion
    float res;
    float[] poids = new float[nbColis]; // poids de chaque colis
    static final int BYTES=Integer.BYTES+2*Float.BYTES;

    public void binResMax() throws IOException {
        FileChannel f = FileChannel.open(
                FileSystems.getDefault().getPath("Camion.bin"),
                StandardOpenOption.READ,
                StandardOpenOption.WRITE
        );
        ByteBuffer buf = ByteBuffer.allocate(BYTES * 102);
        buf.clear();
        while (f.position() != f.size()) { //Ligne pour vérifier que la fin du fichier n'a pas été atteinte
            while (buf.hasRemaining())
                if (f.read(buf) == -1)
                    return;
            buf.flip();
            float tmp = 0;
            id = buf.getInt();
            res = buf.getFloat();
            for (int i = 1; i < nbColis + 1; i++) {//on parcours tous les colis du camion
                poids[i] = buf.getInt();
                if (poids[i] > tmp)
                    tmp = poids[i];//on prend le plus lourd coli du camion comme res
            }
            res = tmp;

            //Affichage pour visualiser, cela fait pas parti du TP car on doit juste modifier
            System.out.print(id);
            System.out.print("|");
            System.out.print(res);
            System.out.print("|");
            for (float p : poids) {
                System.out.print(p);
                System.out.print("|");
            }
            System.out.println(" ");
        }
        f.close();
    }

    public void textToBin() throws FileNotFoundException {
        Scanner in = new Scanner(new File("Camion.txt"));//entre
        PrintWriter out = new PrintWriter("NewCamion.bin");//sortie

        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] split_line = line.split(";");//separateur
            id = Integer.parseInt(split_line[0]);
            res = Float.parseFloat(split_line[0]);
            for (int i = 1; i < nbColis + 1; i++) {
                poids[i] = Integer.parseInt(split_line[i]);
            }
            String str=" "+id + res + poids[];
            char[] messChar = str.toCharArray();
        }
        int result=0;
        for (int i = 0; i < messChar.length; i++) {
            int result += Integer.toBinaryString(messChar[i]) + " ";
        }

        in.close();
        out.close();
    }
    //je pense que j'aurais du utiliser un ByteBuffer et ensuite put
    //je n'ai pas eu le temps, j'ai perdu trop de temps sur cette version




    public static void main(String[] args){ //main pour teste les procédure

        try {
            Camion c = new Camion();
            c.binResMax();
            c.textToBin();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
