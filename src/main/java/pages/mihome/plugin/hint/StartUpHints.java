package pages.mihome.plugin.hint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartUpHints {
	
	List<StartUpHint> startUpHints = Arrays.asList(new PrecautionsHint(),new SaveMapHint(),new LessCollisionModeHint());
	
	public void acceptAllHints() {
		for(StartUpHint hint : startUpHints) {
			hint.acceptHint();
		}
	}
}
