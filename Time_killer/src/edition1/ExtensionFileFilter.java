package edition1;
/**
 * @author Wither
 *
 *
 */
import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ExtensionFileFilter extends FileFilter {
    public String target;
    public String description;

    public ExtensionFileFilter(String target, String description) {
        this.target = target;
        this.description = description;
    }

	@Override
	public String getDescription() {
	        return this.description;
	}

	@Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String file_name = f.getName();
        int index = file_name.lastIndexOf('.');
        if (index > 0 && index < file_name.length() - 1) {
            String extension = file_name.substring(index + 1).toLowerCase();
            if (extension.equals(target))
                return true;
        }
        return false;
    }
}