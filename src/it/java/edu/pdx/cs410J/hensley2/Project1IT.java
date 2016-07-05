package edu.pdx.cs410J.hensley2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static java.util.function.Predicate.isEqual;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Integration tests for the {@link Project1} main class.
 */
public class Project1IT extends InvokeMainTestCase {

  /**
   * Invokes the main method of {@link Project1} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain( Project1.class, args );
  }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  public void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getErr(), containsString("Missing command line arguments"));
    assertThat(result.getOut(), equalTo(""));
  }

  @Test
  public void testTooManyCommandLineOptions(){
    MainMethodResult result = invokeMain("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getErr(), containsString("Too many command line arguments"));
    assertThat(result.getErr(), containsString("Number Entered: 10"));
    assertThat(result.getErr(), containsString("Number Expected: " + CliParser.MAX_COMMAND_LINE_ARGS));
  }

  @Test
  public void testReadmeCommandLineArgument(){
    MainMethodResult result = invokeMain("-README", "-print", "Mike Hensley", "A Description",
                                         "01/01/2016", "04:00", "01/01/2016", "05:00");
    assertThat(result.getOut(), containsString(Project1Constants.README));
    assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getErr(), equalTo(""));
  }

  @Test
  public void testReadmeCommandInSecondPosition(){
    MainMethodResult result = invokeMain("-print", "-README", "Mike Hensley", "A Description",
                                         "01/01/2016", "04:00", "01/01/2016", "05:00");
    System.out.println(result.getErr());
    assertThat(result.getOut(), equalTo(Project1Constants.README));
    assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getErr(), equalTo(""));
  }

    @Test
    public void testInvalidOptionsArgument(){
        MainMethodResult result = invokeMain("-print", "-README", "Mike Hensley", "-InvalidOption", "A Description",
                "01/01/2016", "04:00", "01/01/2016");
        System.out.println(result.getErr());
        assertThat(result.getOut(), containsString(Project1Constants.README));
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getErr(), equalTo(""));
    }
}