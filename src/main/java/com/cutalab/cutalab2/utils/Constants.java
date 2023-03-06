package com.cutalab.cutalab2.utils;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class Constants {

    public static final Integer NOTIFICATION_DURATION = 10000;

    public static final String APP_TITLE = "Luca Mezzolla Website";
    public static final String APP_AUTHOR = "Luca Mezzolla";


    public static final String MENU_ADMIN = "Amministrazione";
    public static final String MENU_AREA_LINK = "Area";
    public static final String MENU_LOGIN = "Login";
    public static final String MENU_LABORATORY = "Laboratorio";
    public static final String MENU_MOMENTS = "Momenti";
    public static final String MENU_CONTACTS = "Contatti";
    public static final String MENU_LINKS = "Links";
    public static final String MENU_DASHBOARDS = "Gestionali";
    public static final String MENU_DASHBOARDS_DISKS = "Gestione dischi";
    public static final String MENU_DASHBOARDS_MAGICABULA = "Gestione magicabula";

    public static final Integer FORM_CREATE = 1;
    public static final Integer FORM_UPDATE = 2;
    public static final Integer FORM_READ = 3;
    public static final Integer FORM_DELETE = 4;


    //AMMINISTRAZIONE
    public final static String AREA_LINK_GRID_HEADER_TITLE = "Aree del links";
    public final static String LINK_GRID_HEADER_TITLE = "Links";
    public final static String URL_GRID_HEADER_TITLE = "URL";

    //PULSANTI
    public static final String SAVE = "Salva";
    public static final String EDIT = "Modifica";
    public static final String REMOVE = "Rimuovi";
    public static final String CANCEL = "Annulla";
    public static final String SEARCH = "Cerca";
    public static final String CLEAR = "Pulisci";
    public static final String DETAIL = "Dettaglio";
    public static final String CLOSE = "Chiudi";
    public static final String CONTINUE = "Procedi";


    //GESTIONALE DISCHI
    public static final String COLLECTION_OF_PLACEHOLDER = "Collezione di...";
    public static final String TITLE_SEARCH_PLACEHOLDER = "Cerca per titolo...";
    public static final String AUTHOR_SEARCH_PLACEHOLDER = "Cerca per autore...";
    public static final String GENRE_PLACEHOLDER = "Seleziona un genere...";
    public static final String STYLE_PLACEHOLDER = "Seleziona uno stile...";
    public static final String NO_DISKS = "Nessun disco è stato trovato in base ai parametri di ricerca.";
    public static final String DISK_DETAIL_OPENABLE = "Apribile?";
    public static final String DISK_DETAIL_COVER = "Copertina";
    public static final String DISK_DETAIL_TITLE = "Titolo";
    public static final String DISK_DETAIL_AUTHOR = "Autore";
    public static final String DISK_DETAIL_LABEL = "Etichetta";
    public static final String DISK_DETAIL_REPRINT = "Ristampa";
    public static final String DISK_DETAIL_VALUE = "Valore presunto";
    public static final String DISK_DETAIL_YEAR = "Anno";
    public static final String DISK_DETAIL_DISK_STATUS = "Stato del disco";
    public static final String DISK_DETAIL_COVER_STATUS = "Stato della copertina";
    public static final String DISK_DETAIL_NOTE = "Note aggiuntive";
    public static final String DISK_DETAIL_GENRE = "Generi";
    public static final String DISK_DETAIL_STYLE = "Stili";
    public static final String DISK_EDIT_GENRE = "Modifica generi";
    public static final String DISK_EDIT_STYLE = "Modifica stili";
    public static final String DISK_ADD_GENRE = "Aggiungi generi";
    public static final String DISK_ADD_STYLE = "Aggiungi stili";
    public static final String DISK_CREATE_TITLE = "Aggiungi un disco";
    public static final String DISK_REMOVE_CONFIRMATION = "Sei sicuro di rimovere questo disco?";

    //**************************************************************************************************************


    //NOTIFICHE
    public static final String DB_OPERATION_SUCCESS = "Operazione eseguita con successo.";
    public static final String DB_OPERATION_ERROR = "Si è verificato un errore durante l'uso del database.";
    public static final String DB_OPERATION_RELATION_ERROR = "Errore. Esistono relazioni che impediscono la modifica o la rimozione.";
    public static final String DB_VALIDATION_ERROR = "Attenzione. I campi inseriti non sono validi.";
    public static final String DB_VALIDATION_DISK_ERROR = "Attenzione. Tutt i campi, tranne il campo delle note, sono richiesti.";
    public static final String DB_VALIDATION_INDEX_DISKS_ERROR = "Indicare il nome del proprietario della collezione.";

    public final static void NOTIFICATION_DB_SUCCESS() {
        Notification notification = Notification.show(DB_OPERATION_SUCCESS);
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
    }

    public final static void NOTIFICATION_DB_ERROR(Exception e) {
        String message = e.getMessage().contains("ConstraintViolationException") ? DB_OPERATION_RELATION_ERROR : DB_OPERATION_ERROR;
        Notification notification = Notification.show(message);
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setDuration(NOTIFICATION_DURATION);
    }

    public final static void NOTIFICATION_DB_VALIDATION_ERROR() {
        Notification notification = Notification.show(DB_VALIDATION_ERROR);
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setDuration(NOTIFICATION_DURATION);
    }

    public final static void NOTIFICATION_DB_VALIDATION_DISK_ERROR() {
        Notification notification = Notification.show(DB_VALIDATION_DISK_ERROR);
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setDuration(NOTIFICATION_DURATION);
    }

    public final static void NOTIFICATION_DB_VALIDATION_INDEX_DISKS_ERROR() {
        Notification notification = Notification.show(DB_VALIDATION_INDEX_DISKS_ERROR);
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setDuration(NOTIFICATION_DURATION);
    }

    public final static void NOTIFICATION_NO_DISK() {
        Notification notification = Notification.show(NO_DISKS);
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
        notification.setDuration(NOTIFICATION_DURATION);
    }


    //**************************************************************************************************************




}