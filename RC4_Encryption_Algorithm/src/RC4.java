import java.util.Scanner;


public class RC4 {

	/*
	 * Veriler byte byte þifrelenir.
	 * Anahtarýn uzunluðu plain text kadardýr.
	 * Plain text'in ilk byte'ý ile anahtarýn ilk byte'ý özel veya (XOR) iþlemine tabi tutulur.
	 * Anahtar dizi þeklinde düþünülebilir bu durumda anahtar max 256 byte uzunluðunda olur.
	 * Anahtarýmýz 256 byte uzunluðunda ise olduðu gibi diziye yazýlýr daha kýsa ise kendini tekrar ederek yazýlýr.
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
				 * Baslangic dizisi : 0 1 2 3 4 5 6 7 .... 255 Þeklinde indislerine elemanlar
				 * yerleþtirildi. Anahtar diziside girilen anahtara göre indislerine elemanlar
				 * yerleþtirildi.
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

				/* sifreli metni oluþturdum */
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
		System.out.print("Lütfen Anahtar Giriniz : ");
		String anahtar = klavye.nextLine();
		System.out.print("Lütfen Þifrelenecek Metni Giriniz : ");
		String sifrelenecekMetin = klavye.nextLine();

		/*
		 * Kullanýcýnýn girmiþ olduðu anahtara ve metne göre þifreleme iþlemine
		 * baþlayalým.
		 */
		rc4.baslangicPermutasyonu(anahtar);
		rc4.ksa();
		rc4.prgaVeXor(sifrelenecekMetin);

		/* Þifreleme iþlemi tamamlandý. */
		String sifrelenmisMetin = rc4.sifreliMetin;

		System.out.println("Þifreli Metin -----> " + sifrelenmisMetin);

		/* Kullanýcýnýn girmiþ olduðu anahtara göre þifreli metni çözüyorum. */
		System.out.print("\nÞifreyi çözmek için anahtarý giriniz : ");
		String sifreyiCozecekAnahtar = klavye.nextLine();

		rc4.baslangicPermutasyonu(sifreyiCozecekAnahtar);
		rc4.ksa();
		rc4.prgaVeXor(sifrelenmisMetin);

		char[] sifresiCozulmusMetin = rc4.sifresiCozulmusMetin;

		System.out.print("Þifresi çözülmüþ metin ----> ");

		for (int i = 0; i < sifresiCozulmusMetin.length; i++) {
			System.out.print(sifresiCozulmusMetin[i]);
		}
	}

	}
