/**
 * special exception class to report if the parameters have not been initialized.
 */
public class ParametersUninitializedException extends Exception
{
    public ParametersUninitializedException(String message)
    {
        super("The parameters to the project, such as page size and number of pages, were not initialized in Controller" + message);
    }
}
