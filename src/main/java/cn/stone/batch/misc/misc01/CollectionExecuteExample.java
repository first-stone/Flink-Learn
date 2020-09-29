package cn.stone.batch.misc.misc01;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.List;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 9:59 2020/9/19
 * @Modified By:
 */
public class CollectionExecuteExample {
    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.createCollectionsEnvironment();

        // create objects for users and emails
        User[] usersArray = { new User(1, "Peter"), new User(2, "John"), new User(3, "Bill") };

        EMail[] emailsArray = {new EMail(1, "Re: Meeting", "How about 1pm?"),
                new EMail(1, "Re: Meeting", "Sorry, I'm not availble"),
                new EMail(3, "Re: Re: Project proposal", "Give me a few more days to think about it.")};

        // convert objects into a DataSet
        DataSet<User> users = env.fromElements(usersArray);
        DataSet<EMail> emails = env.fromElements(emailsArray);

        // join the two DataSets
        DataSet<Tuple2<User, EMail>> joined = users
                .join(emails)
                .where("userIdentifier")
                .equalTo("userId");

        // retrieve the resulting Tuple2 elements into a ArrayList.
        List<Tuple2<User, EMail>> result = joined.collect();

        // Do some work with the resulting ArrayList (=Collection).
        for (Tuple2<User, EMail> t : result) {
            System.err.println("Result = " + t);
        }
    }
}
