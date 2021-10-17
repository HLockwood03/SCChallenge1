import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;

public class SC1 {

  public BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  public String id;

  /**
   * Takes the URL as a parameter, opens a connection to the website and starts scanning it
   * Since the location of the list of people is known, the method will only look on the lines
   * where names are contained.
   *
   * @param url
   * @throws IOException
   */
  public void getPage(URL url) throws IOException {

    Scanner scanner = new Scanner(url.openStream());
    String line;
    Boolean scanning = false;

    while (scanner.hasNext()) {
      // buffer.append(scanner.next());
      line = scanner.nextLine();

      if (line.equals("")) {
        scanning = false;
      }

      if (scanning) {
        findName(line);
      }

      if (line.contains("<tbody class=\"list\">")) {
        scanning = true;
      }
    }

  }

  /**
   * Uses given ID to look inside the line and see if our person is within it.
   * The ID can take several forms, as the id used in a person's email may be different to their actual ID
   * i.e for Dr George Konstantinidis, his email is g.konstantinidis@soton.ac.uk but his ID is gk1e17.
   * Hence we must consider this difference
   * It is well known exactly where the name of a person is contained in the list so the unnecessary parts without the
   * name can be removed just by knowing where the name is.
   *
   * @param line
   * @return
   */
  public String findName(String line) {
    if (line.contains("people/" + id + "\">") || line.contains(id + "@")) {
      line = (line.substring(line.indexOf("people/"), line.length()));

      line = line.substring(line.indexOf(">") + 1, line.indexOf("<"));

      System.out.println(id + " is " + line);
    }
    return "";
  }

  public void enterID() throws IOException {
    System.out.print("Please enter the ID of the person you are looking for: ");
    id = br.readLine();
  }

  public static void main(String[] args) throws IOException {
    SC1 read  = new SC1();
    read.enterID();
    read.getPage(new URL("https://www.ecs.soton.ac.uk/people/"));
  }
}
