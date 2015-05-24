package rmi;

import datas.Hashtag;
import datas.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Tom Veniat on 21/05/15.
 */
public interface Twitter extends Remote {

    /**
     * Fonction permettant de créer un nouvel utilisateur.
     *
     * @param username nom du nouvel utilisateur
     * @param password mot de passe du nouvel utilisateur
     * @return true si l'utilisateur a été créé, false sinon.
     * @throws RemoteException
     */
    boolean newAccount(String username, String password) throws RemoteException;

    /**
     * Permet la connection d'un utilisateur
     *
     * @param username nom de l'utilisateur
     * @param password mot de passe l'utilisateur
     * @return l'utilisateur s'étant connecté si la connection a fonctionné, null sinon.
     * @throws RemoteException
     */
    User connect(String username, String password) throws RemoteException;

    /**
     * Permet la creation d'un nouveau hastag
     *
     * @param hashtag nom du nouvel hashtag
     * @return true si l'utilisateur a été créé, false sinon.
     * @throws RemoteException
     */
    boolean createHashtag(String hashtag ) throws RemoteException;

    /**
     * Abonne l'utilisateur au hashtag
     * Cette fonction crée un nouveau hashtag si celui ci n'éxiste pas.
     *
     * @param username
     * @param hashtag
     * @return true si l'abonnement a réussi, false sinon.
     * @throws RemoteException
     */
    boolean followHashtag(String username, String hashtag ) throws RemoteException;

    /**
     * Abonne l'utilisateur à un autre utilisateur.
     *
     * @param username l'utilisateur qui souhaite s'abonner
     * @param followedUser l'utilisateur auquel il souhaite s'abonner
     * @return true en cas de réussite, false sinon
     * @throws RemoteException
     */
    boolean followUser(String username, String followedUser) throws RemoteException;

    /**
     *Fonction permettant de récuperer tous les utilisateurs existants.
     *
     * @return la liste des utilisateurs.
     * @throws RemoteException
     */
    List<User> allUsers() throws RemoteException;

    /**
     *Fonction permettant de récuperer tous les hashtags existants.
     *
     * @return la liste des hashtags.
     * @throws RemoteException
     */
    List<Hashtag> allHashtags() throws RemoteException;

    /**
     * permet de savoir si un utilisateur ou un hashatg portant ce nom existe.
     *
     * @param targetName
     * @return
     * @throws RemoteException
     */
    boolean existsTarget(String targetName) throws RemoteException;

    /**
     * permet de savoir si quelque un utilisateur portant ce nom existe.
     * @param userName
     * @return
     * @throws RemoteException
     */
    boolean existsUser(String userName) throws RemoteException;

    /**
     * permet de savoir si quelque un utilisateur ou un hashatg portant ce nom existe.
     *
     * @param hashtagName
     * @return
     * @throws RemoteException
     */
    boolean existsHashtag(String hashtagName) throws RemoteException;

}
