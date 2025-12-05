import pexpect

def test_save_restore():
    baza = pexpect.pexpect()

    try:
        baza.expect('Ukaz> ')

        baza.send('uporabi 23')
        baza.expect('V redu')
        baza.expect('Ukaz> ')

        baza.send('dodaj')
        baza.expect('dodaj>VPISNA TEVILKA: ')
        baza.send('63230453')
        baza.expect('dodaj>IME: ')
        baza.send('John')
        baza.expect('dodaj>PRIIMEK: ')
        baza.send('Wick')
        baza.expect('dodaj>PROGRAM: ')
        baza.send('VSS RI')
        baza.expect('dodaj>LETO DIPLOME: ')
        baza.send('2026')
        baza.expect('dodaj>NASLOV DIPLOME: ')
        baza.send('Naslov Diplome')
        baza.expect('V redu')
        baza.expect('Ukaz> ')

        baza.send('prestej')
        baza.expect('1')
        baza.expect('Ukaz> ')

        baza.send('dodaj')
        baza.expect('dodaj>VPISNA TEVILKA: ')
        baza.send('63230442')
        baza.expect('dodaj>IME: ')
        baza.send('Ana De')
        baza.expect('dodaj>PRIIMEK: ')
        baza.send('Armas')
        baza.expect('dodaj>PROGRAM: ')
        baza.send('VSS RI')
        baza.expect('dodaj>LETO DIPLOME: ')
        baza.send('2026')
        baza.expect('dodaj>NASLOV DIPLOME: ')
        baza.send('Naslov Diplome')
        baza.expect('V redu')
        baza.expect('Ukaz> ')

        baza.send('prestej')
        baza.expect('2')
        baza.expect('Ukaz> ')

        baza.send('dodaj')
        baza.expect('dodaj>VPISNA TEVILKA: ')
        baza.send('63239957')
        baza.expect('dodaj>IME: ')
        baza.send('Robert De')
        baza.expect('dodaj>PRIIMEK: ')
        baza.send('Niro')
        baza.expect('dodaj>PROGRAM: ')
        baza.send('VSS RI')
        baza.expect('dodaj>LETO DIPLOME: ')
        baza.send('2026')
        baza.expect('dodaj>NASLOV DIPLOME: ')
        baza.send('Naslov Diplome')
        baza.expect('V redu')
        baza.expect('Ukaz> ')

        baza.send('prestej')
        baza.expect('3')
        baza.expect('Ukaz> ')

        baza.send('izpisi')
        baza.expect('Struktura po imenu in priimku:')
        baza.expect('\t63239957 | Niro, Robert De | VSS RI | 2026 | Naslov Diplome')
        baza.expect('\t63230442 | Armas, Ana De | VSS RI | 2026 | Naslov Diplome')
        baza.expect('\t63230453 | Wick, John | VSS RI | 2026 | Naslov Diplome')
        baza.expect('Struktura po vpisno stevilko:')
        baza.expect('\t63230453 | Wick, John | VSS RI | 2026 | Naslov Diplome')
        baza.expect('\t63239957 | Niro, Robert De | VSS RI | 2026 | Naslov Diplome')
        baza.expect('\t63230442 | Armas, Ana De | VSS RI | 2026 | Naslov Diplome')
        baza.expect('V redu')
        baza.expect('Ukaz> ')

        baza.send('shrani data.bin')
        baza.expect('V redu')
        baza.expect('Ukaz> ')

        baza.send('povrni data.bin')
        baza.expect('V redu')
        baza.expect('Ukaz> ')

        baza.send('izpisi')
        baza.expect('Struktura po imenu in priimku:')
        baza.expect('\t63239957 | Niro, Robert De | VSS RI | 2026 | Naslov Diplome')
        baza.expect('\t63230442 | Armas, Ana De | VSS RI | 2026 | Naslov Diplome')
        baza.expect('\t63230453 | Wick, John | VSS RI | 2026 | Naslov Diplome')
        baza.expect('Struktura po vpisno stevilko:')
        baza.expect('\t63230453 | Wick, John | VSS RI | 2026 | Naslov Diplome')
        baza.expect('\t63239957 | Niro, Robert De | VSS RI | 2026 | Naslov Diplome')
        baza.expect('\t63230442 | Armas, Ana De | VSS RI | 2026 | Naslov Diplome')
        baza.expect('V redu')
        baza.expect('Ukaz> ')

        baza.send('izhod')
        baza.expect('Nasvidenje')

        print ("PASSED\ttest_save_restore")

    except:
        print ("FAILED\ttest_save_restore")

    finally:
        baza.kill()


if __name__ == "__main__":
    test_save_restore()

