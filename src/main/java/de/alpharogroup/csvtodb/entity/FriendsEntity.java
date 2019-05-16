package de.alpharogroup.csvtodb.entity;

import javax.persistence.Entity;

import de.alpharogroup.db.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "friends")
public class FriendsEntity extends BaseEntity<Integer> {

	/** The serial Version UID. */
	private static final long serialVersionUID = 1L;

	String firstname;
	String lastname;
	String city;
}
