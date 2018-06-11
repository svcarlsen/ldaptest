package dk.kb.brugerbase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.SearchScope;
import org.springframework.stereotype.Service;

import javax.naming.directory.*;
import javax.naming.NamingException;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;


/**
 * Created by dgj on 07-06-2018.
 */
@Service
public class PersonRepository {

    private static Logger log = LoggerFactory.getLogger(PersonRepository.class);


    @Autowired
    private LdapTemplate ldapTemplate;


    public void createPerson(Person person) {
        String dn = "uid="+person.getUid()+",ou=People,dc=example,dc=lan";
        Attributes attrs = new BasicAttributes();
        BasicAttribute ocattr = new BasicAttribute("objectclass");
        ocattr.add("top");
        ocattr.add("person");
        ocattr.add("eduPerson");
        ocattr.add("inetOrgPerson");
        attrs.put(ocattr);
        attrs.put("cn", person.getFullName());
        attrs.put("sn", person.getLastName());
        attrs.put("mail", person.getMail());
        ldapTemplate.bind(dn, null, attrs);
    }

    public void deletePerson(Person person) {
        String dn = "uid="+person.getUid()+",ou=People,dc=example,dc=lan";
        ldapTemplate.unbind(dn);
    }

    public void modifyAttribute(String uid, String attribute, String value) {
        String dn = "uid="+uid+",ou=People,dc=example,dc=lan";
        Attribute attr = new BasicAttribute(attribute, value);
        ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
        ldapTemplate.modifyAttributes(dn, new ModificationItem[] {item});
    }

    public List<Person> getPeople() {
        LdapQuery query = query()
                .searchScope(SearchScope.SUBTREE)
                .countLimit(10)
                .attributes("*")
                .base("ou=People,dc=example,dc=lan")
                .where("objectClass").is("person");

        return ldapTemplate.search(query, new PersonAttributesMapper());
    }

    public Person getPerson(String uid) {
        LdapQuery query = query()
                .searchScope(SearchScope.SUBTREE)
                .countLimit(10)
                .attributes("*")
                .base("ou=People,dc=example,dc=lan")
                .where("uid").is(uid);

        List<Person> result = ldapTemplate.search(query, new PersonAttributesMapper());

        if (result.size() > 0)
            return result.get(0);
        return null;

    }



    private class PersonAttributesMapper implements AttributesMapper<Person> {
        public Person mapFromAttributes(Attributes attrs) throws NamingException {
            Person person = new Person();
            person.setFullName((String)attrs.get("cn").get());

            Attribute sn = attrs.get("sn");
            if (sn != null){
                person.setLastName((String)sn.get());
            }

            Attribute mail = attrs.get("mail");
            if (mail != null){
                person.setMail((String)mail.get());
            }

            Attribute uid = attrs.get("uid");
            if (uid != null){
                person.setUid((String)uid.get());
            }

            return person;
        }
    }
}
