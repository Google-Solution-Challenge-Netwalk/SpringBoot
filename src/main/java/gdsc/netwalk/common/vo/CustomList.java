package gdsc.netwalk.common.vo;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@NoArgsConstructor
@Qualifier("customList")
public class CustomList<E> extends ArrayList<E>{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public CustomList(List<?> list) {
		if (list != null) {
			Iterator<?> it = list.iterator();
			while (it.hasNext()) {
				this.add((E) it.next());
			}
		}
	}

	public void set(E obj) {
		super.add(obj);
	}

	public void set(E[] objs) {
		for (E obj : objs) {
			super.add(obj);
		}
	}
}
