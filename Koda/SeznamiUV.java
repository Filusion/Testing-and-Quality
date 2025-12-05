package domaca_naloga2;


import java.io.*;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class SeznamiUV {

    HashMap<String, Seznam<Diplomat>> seznamiPoImenu;
    HashMap<String, Seznam<Diplomat>> seznamiPoVpSt;
    Seznam<Diplomat> seznamPoImenu;
    Seznam<Diplomat> seznamPoVpSt;
    
    static String memoryError = "Premalo pomnilnika, operacija ni uspela";
   
    public SeznamiUV() {
        seznamiPoImenu = new HashMap<>();
        seznamiPoVpSt = new HashMap<>();
    }
    
    public void addImpl(String key, Seznam<Diplomat> seznamPoImenu, Seznam<Diplomat> seznamPoVpSt) {
        seznamiPoImenu.put(key, seznamPoImenu);
        seznamiPoVpSt.put(key, seznamPoVpSt);
    }

    public String processInput(String input) {
        String token;
        String result = "V redu";
        String[] params = input.split(" ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        //moramo preverjati za primer praznega Niza
        if (input.isEmpty()) {
            return "Vnesite ukaz";
        }
        else{
            token = params[0];
        }

        if (!token.equals("uporabi") && (null == seznamPoImenu)) {
            return "Prosim, navedite podatkovno strukturo (uporabi {23})";
        }
        try {
            if (token.equals("uporabi")){
                if (params.length > 1) {
                    String structType = params[1];
                    seznamPoImenu = seznamiPoImenu.get(structType);
                    seznamPoVpSt = seznamiPoVpSt.get(structType);
                    if (null == seznamPoImenu) {
                        result = "Prosim, navedite pravilen tip podatkovne strukture {23}";
                    }

                } else {
                    result = "Prosim, navedite podatkovno strukturo {23}";
                }
            }
            else if (token.equals("dodaj")){
                System.out.print("dodaj>VPISNA ŠTEVILKA: ");
                String vpStevilka = br.readLine();
                if (!verifyVpSt(vpStevilka)) {
                    result = "Napačni podatki";
                    return result;
                }
                System.out.print("dodaj>IME: ");
                String ime = br.readLine();
                System.out.print("dodaj>PRIIMEK: ");
                String priimek = br.readLine();
                System.out.print("dodaj>PROGRAM: ");
                String program = br.readLine();
                if (!verifyProgram(program)) {
                    result = "Napačni podatki";
                    return result;
                }
                System.out.print("dodaj>LETO DIPLOME: ");
                String letoDiplome = br.readLine();
                if (!verifyLetoDiplome(letoDiplome, vpStevilka)) {
                    result = "Napačni podatki";
                    return result;
                }
                System.out.print("dodaj>NASLOV DIPLOME: ");
                String naslovDiplome = br.readLine();

                seznamPoImenu.add(new Diplomat(ime,priimek, vpStevilka, program, letoDiplome, naslovDiplome));
                seznamPoVpSt.add(new Diplomat(ime,priimek, vpStevilka, program, letoDiplome, naslovDiplome));
            }
            else if (token.equals("odstrani")){
                if (params.length == 2)  {
                    String vpisnaSt = params[1];
                    if (!verifyVpSt(vpisnaSt)) {
                        result = "Napačni podatki";
                        return result;
                    }
                    if (seznamPoVpSt.exists(new Diplomat("", "", vpisnaSt, "", "", ""))) {
                        Diplomat zaBrisanje = seznamPoVpSt.getElement(new Diplomat("", "", vpisnaSt, "", "", ""));
                        seznamPoVpSt.remove(new Diplomat("", "", vpisnaSt, "", "", ""));
                        seznamPoImenu.remove(new Diplomat(zaBrisanje.getIme(), zaBrisanje.getPriimek(), "", "", "", ""));
                    }
                    else {
                        result = "Diplomant ne obstaja";
                        return result;
                    }
                }
                else {
                    System.out.print("odstrani>IME: ");
                    String ime = br.readLine();
                    System.out.print("odstrani>PRIIMEK: ");
                    String priimek = br.readLine();
                    if (seznamPoImenu.exists(new Diplomat(ime, priimek, "", "", "", ""))) {
                        Diplomat zaBrisanje = seznamPoImenu.getElement(new Diplomat(ime, priimek, "", "", "", ""));
                        seznamPoImenu.remove(new Diplomat(ime, priimek, "", "", "", ""));
                        seznamPoVpSt.remove(new Diplomat("", "", zaBrisanje.getVpisnaStevilka(), "", "", ""));
                    }
                    else {
                        result = "Diplomant ne obstaja";
                        return result;
                    }
                }
            }
            else if (token.equals("poisci")) {
                if (params.length == 2) {
                    String vpisnaSt = params[1];
                    if (!verifyVpSt(vpisnaSt)) {
                        result = "Napačni podatki";
                        return result;
                    }
                    if (seznamPoVpSt.exists(new Diplomat("", "", vpisnaSt, "", "", ""))) {
                        result = seznamPoVpSt.getElement(new Diplomat("", "", vpisnaSt, "", "", "")).toString();
                    }
                    else {
                        result = "Diplomant ne obstaja";
                        return result;
                    }
                }
                else {
                    System.out.print("poisci>IME: ");
                    String ime = br.readLine();
                    System.out.print("poisci>Priimek: ");
                    String priimek = br.readLine();
                    if (seznamPoImenu.exists(new Diplomat(ime, priimek, "", "", "", ""))) {
                        result = seznamPoImenu.getElement(new Diplomat(ime, priimek, "", "", "", "")).toString();
                    }
                    else {
                        result = "Diplomant ne obstaja";
                        return result;
                    }
                }
            }
            else if (token.equals("prestej")){
                result = String.valueOf(seznamPoImenu.size());
            }

            else if (token.equals("izprazni")){
                System.out.print("izprazni>Ste prepičani? (d|n):");
                String answer = br.readLine();
                if (answer.equals("d")) {
                    while (!seznamPoImenu.isEmpty()) {
                        seznamPoImenu.removeFirst();
                    }
                    while (!seznamPoVpSt.isEmpty()) {
                        seznamPoVpSt.removeFirst();
                    }
                }
                else {
                    result = "Ukaz je bil preklican";
                    return result;
                }
            }
            else if (token.equals("izpisi")){
                System.out.println("Struktura po imenu in priimku:");
                seznamPoImenu.print();
                System.out.println("Struktura po vpisno stevilko:");
                seznamPoVpSt.print();
            }
            else if (token.equals("shrani")){
                if (params.length == 2) {
                    String file_name = params[1];
                    int dotIndex = file_name.lastIndexOf('.');
                    String baseName = (dotIndex != -1) ? file_name.substring(0, dotIndex) : file_name;
                    String extension = (dotIndex != -1) ? file_name.substring(dotIndex) : "";

                    String file_po_imenu = baseName + "_p" + extension;
                    String file_po_tel = baseName + "_v" + extension;

                    seznamPoImenu.save(new FileOutputStream(file_po_imenu));
                    seznamPoVpSt.save(new FileOutputStream(file_po_tel));
                } else {
                    result = "Prosim, navedite ime datoteke";
                    return result;
                }
            }
            else if (token.equals("povrni")){
                if (params.length == 2) {
                    String file_name = params[1];
                    int dotIndex = file_name.lastIndexOf('.');
                    String baseName = (dotIndex != -1) ? file_name.substring(0, dotIndex) : file_name;
                    String extension = (dotIndex != -1) ? file_name.substring(dotIndex) : "";

                    String file_po_imenu = baseName + "_p" + extension;
                    String file_po_tel = baseName + "_v" + extension;

                    seznamPoImenu.restore(new FileInputStream(file_po_imenu));
                    seznamPoVpSt.restore(new FileInputStream(file_po_tel));
                } else {
                    result = "Prosim, navedite ime datoteke";
                    return result;
                }
            }
            else if (token.equals("izhod")) {
                result = "Nasvidenje";
            }
            else {
                result = "Napačen ukaz";
            }
                

        } catch (UnsupportedOperationException e) {
            result = "Operacija ni podprta";
        } catch (IllegalArgumentException e) {
            result = "Diplomant že obstaja";
        } catch (java.util.NoSuchElementException e) {
            result = "Podatkovna struktura je prazna";
        } catch (IOException e) {
            result = "I/O napaka " + e.getMessage();
        } catch (ClassNotFoundException e) {
            result = "Neznana oblika";
        } catch (OutOfMemoryError e) {
            return memoryError;
        }

        return result;
    }

    private boolean verifyVpSt(String vpisnaSt) {
        if (vpisnaSt.charAt(0) != '6' || vpisnaSt.charAt(1) != '3')
            return false;

        int sum = 0;
        for (int i = 0; i < vpisnaSt.length() - 1; i++) {
            int digit = Character.getNumericValue(vpisnaSt.charAt(i));
            sum += digit;
        }

        int K = sum % 10;
        int lastDigit = Character.getNumericValue(vpisnaSt.charAt(vpisnaSt.length() - 1));

        if (lastDigit != K)
            return false;

        return true;
    }

    private boolean verifyProgram(String program) {
        String[] programi = {"VSS RI", "UNI RI", "UNI ISRM", "MAG RI", "DR RI"};
        for (int i = 0; i < programi.length; i++) {
            if (program.equals(programi[i]))
                return true;
        }

        return false;
    }

    private boolean verifyLetoDiplome(String letoDiplome, String vpisnaSt) {
        int vpLeto = Integer.parseInt(vpisnaSt.substring(2, 4));
        int leto = Integer.parseInt(letoDiplome.substring(2));
        if (leto - vpLeto >= 2)
            return true;

        return false;
    }
}
