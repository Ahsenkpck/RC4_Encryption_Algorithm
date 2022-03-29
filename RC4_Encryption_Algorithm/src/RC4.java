import java.util.Scanner;


public class RC4 {

	/*
	 * Veriler byte byte �ifrelenir.
	 * Anahtar�n uzunlu�u plain text kadard�r.
	 * Plain text'in ilk byte'� ile anahtar�n ilk byte'� �zel veya (XOR) i�lemine tabi tutulur.
	 * Anahtar dizi �eklinde d���n�lebilir bu durumda anahtar max 256 byte uzunlu�unda olur.
	 * Anahtar�m�z 256 byte uzunlu�unda ise oldu�u gibi diziye yaz�l�r daha k�sa ise kendini tekrar ederek yaz�l�r.
	 * */
		int[] baslangicDizisi = new int[256];
		int[] anahtarDizisi = new int[256];

		int baslangicDizisiUzunlugu = baslangicDizisi.length;

		String sifreliMetin = "";
		char[] sifresiCozulmusMetin;

		public void baslangicPermutasyonu(String anahtar) {

			int anahtarUzunlugu = anahtar.length();
			for (int i = 0; i < baslangicDizisiUzunlugu; i++) {
				/*
				 * Baslangic dizisi : 0 1 2 3 4 5 6 7 .... 255 �eklinde indislerine elemanlar
				 * yerle�tirildi. Anahtar diziside girilen anahtara g�re indislerine elemanlar
				 * yerle�tirildi.
				 */
				baslangicDizisi[i] = i;
				anahtarDizisi[i] = anahtar.charAt(i % anahtarUzunlugu);
			}
		}

		public void ksa() {
			int j = 0;
			for (int i = 0; i < baslangicDizisiUzunlugu; i++) {
				j = (j + baslangicDizisi[i] + anahtarDizisi[i]) % 256;
				yerDegistir(baslangicDizisi, i, j);
			}
		}

		public void prgaVeXor(String sifrelenecekMetin) {
			int i = 0, j = 0, c = 0, k = 0;
			int sifrelenecekMetinUzunlugu = sifrelenecekMetin.length();
			sifresiCozulmusMetin = new char[sifrelenecekMetinUzunlugu];
			while (sifrelenecekMetinUzunlugu > 0) {
				i = (i + 1) % 256;
				j = (j + baslangicDizisi[i]) % 256;
				yerDegistir(baslangicDizisi, i, j);

				/* sifreli metni olu�turdum */
				k = baslangicDizisi[(baslangicDizisi[i] + baslangicDizisi[j]) % 256];
				sifreliMetin = sifreliMetin + (char) (k ^ sifrelenecekMetin.charAt(i - 1));
				sifresiCozulmusMetin[c] = (char) (k ^ sifrelenecekMetin.charAt(i - 1));
				sifrelenecekMetinUzunlugu = sifrelenecekMetinUzunlugu - 1;
				c++;
			}
		}

		public void yerDegistir(int[] degistirilecekDizi, int indis1, int indis2) {
			int yedek = degistirilecekDizi[indis1];
			degistirilecekDizi[indis1] = degistirilecekDizi[indis2];
			degistirilecekDizi[indis2] = yedek;
		}
	public static void main(String[] args) {
		RC4 rc4 = new RC4();

		Scanner klavye = new Scanner(System.in);
		System.out.print("L�tfen Anahtar Giriniz : ");
		String anahtar = klavye.nextLine();
		System.out.print("L�tfen �ifrelenecek Metni Giriniz : ");
		String sifrelenecekMetin = klavye.nextLine();

		/*
		 * Kullan�c�n�n girmi� oldu�u anahtara ve metne g�re �ifreleme i�lemine
		 * ba�layal�m.
		 */
		rc4.baslangicPermutasyonu(anahtar);
		rc4.ksa();
		rc4.prgaVeXor(sifrelenecekMetin);

		/* �ifreleme i�lemi tamamland�. */
		String sifrelenmisMetin = rc4.sifreliMetin;

		System.out.println("�ifreli Metin -----> " + sifrelenmisMetin);

		/* Kullan�c�n�n girmi� oldu�u anahtara g�re �ifreli metni ��z�yorum. */
		System.out.print("\n�ifreyi ��zmek i�in anahtar� giriniz : ");
		String sifreyiCozecekAnahtar = klavye.nextLine();

		rc4.baslangicPermutasyonu(sifreyiCozecekAnahtar);
		rc4.ksa();
		rc4.prgaVeXor(sifrelenmisMetin);

		char[] sifresiCozulmusMetin = rc4.sifresiCozulmusMetin;

		System.out.print("�ifresi ��z�lm�� metin ----> ");

		for (int i = 0; i < sifresiCozulmusMetin.length; i++) {
			System.out.print(sifresiCozulmusMetin[i]);
		}
	}

	}
