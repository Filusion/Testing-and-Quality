package domaca_naloga2;

import java.io.*;

public class Aplikacija
{
    public static void main(String[] args)
    {
        SeznamiUV seznamiUV = new SeznamiUV();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        String output;

        seznamiUV.addImpl("23", new Drevo23<>(new DiplomatPrimerjajPoImenu()), new Drevo23<>(new DiplomatPrimerjajPoVpSt()));

        try
        {
            do
            {
                System.out.print("Ukaz> ");
                input = br.readLine();
                output = seznamiUV.processInput(input);
                System.out.println(output);
            }
            while (!input.equalsIgnoreCase("izhod"));
        }
        catch (IOException e)
        {
            System.err.println("Failed to retrieve the next command.");
            System.exit(1);
        }
    }
}
