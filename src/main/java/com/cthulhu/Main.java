package com.cthulhu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws JSONException, KeyManagementException, NoSuchAlgorithmException {

        // Bypass Namefake API https cert business
        com.cthulhu.CertTrust.trustMgr();

        newCharacterData.configureStats();

        System.out.println(newCharacterData.charName);
        System.out.println(newCharacterData.hitPoints);
        System.out.println(newCharacterData.strVal);
        System.out.println(newCharacterData.intelVal);
        System.out.println(newCharacterData.conVal);
        System.out.println(newCharacterData.appVal);
        System.out.println(newCharacterData.powVal);
        System.out.println(newCharacterData.sizeVal);
        System.out.println(newCharacterData.eduVal);
        System.out.println(newCharacterData.dexVal);
        System.out.println(newCharacterData.luckVal);

    }

    public static class characterOptions {

        public enum characteristics {
            Strength ("strength"),
            Dexterity ("dexterity"),
            Intelligence ("intelligence"),
            Constitution ("constitution"),
            Appearance ("appearance"),
            Power ("power"),
            Size ("size"),
            Education ("education");

            public String characteristic;

            characteristics(String name) {
                this.characteristic = name;
            }

            @Override
            public String toString() {
                return characteristic;
            }
        };

        public enum skills { Accounting, Anthropology, Appraise, Archaeology, ArtCraft, Charm, Climb, CreditRating, ChtulhuMythos, Disguise, Dodge, DriveAuto, ElectricRepair, FastTalk, FightingBrawl, FirearmsHandgun, FirearmsRifleShotgun, FirstAid, History, Intimidate, Jump, LanguageOther, LanguageOwn, Law, LibraryUse, Listen, Locksmith, MechanicalRepair, Medicine, NaturalWorld, Navigate, Occult, OperateHeavyMachine, Persuade, Pilot, Psychology, Psychoanalysis, Ride, Science, SleightOfHand, SpotHidden, Stealth, Survival, Swim, Throw, Track };

        public enum occupations { Antiquarian, Artist, Athlete, Author, Clergy, Criminal, Dilettante, DoctorOfMedicine, Drifter, Engineer, Entertainer, Farmer, Journalist, Lawyer, Librarian, MilitaryOfficer, Missionary, Musician, Parapsychologist, PoliceDetective, PoliceOfficer, PrivateInvestigator, Professor, Soldier, TribeMember, Zealot};

    }

    public static class newCharacterData {

        private static String charName;

        static {
            try {
                charName = newCharacterName(com.cthulhu.Globals.apiUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Vars for the main characteristic stats
        private static String strVal; // Strength
        private static String intelVal; // Intelligence
        private static String conVal; // Constitution
        private static String sizeVal; // Size
        private static String dexVal; // Dexterity
        private static String appVal; // Appearance
        private static String powVal; // Power
        private static String eduVal; // Education

        private static String luckVal = "Luck: " + Integer.toString(com.cthulhu.RollDice.rollDSix(3));

        // Derived attributes
        private static int conBase;
        private static int sizeBase;

        private static String hitPoints;


        public static String newCharacterName(String url) throws IOException {

            // Hit that API and fetch the data
            JSONObject json = com.cthulhu.NameFetch.readJsonFromUrl(Globals.apiUrl);

            String name = (String) json.get("name");

            return name;

        }

        public static void configureStats() {


            int i;

            for (characterOptions.characteristics characteristic : characterOptions.characteristics.values()) {

                int thisScore;

                // Switch to determine stat and assign score
                switch(characteristic.toString()) {

                    case "strength":
                       thisScore = com.cthulhu.RollDice.rollDSix(3);
                       strVal = "STR: " + Integer.toString(thisScore * 5) + "\t" + (thisScore * 5) / 2 + "\t" + thisScore;
                       break;

                    case "dexterity":
                        thisScore = com.cthulhu.RollDice.rollDSix(3);
                        dexVal = "DEX: " + Integer.toString(thisScore * 5) + "\t" + (thisScore * 5) / 2 + "\t" + thisScore;

                    case "intelligence":
                        thisScore = com.cthulhu.RollDice.rollDSix(2);
                        intelVal = "INT: " + Integer.toString((thisScore + 6) * 5) + "\t" + (((thisScore + 6) * 5) / 2) + "\t" + (thisScore + 6);

                    case "constitution":
                        thisScore = com.cthulhu.RollDice.rollDSix(3);
                        conBase = thisScore;
                        conVal = "CON: " + Integer.toString(thisScore * 5) + "\t" + (thisScore * 5) / 2 + "\t" + thisScore;

                    case "appearance":
                        thisScore = com.cthulhu.RollDice.rollDSix(3);
                        appVal = "APP: " + Integer.toString(thisScore * 5) + "\t" + (thisScore * 5) / 2 + "\t" + thisScore;

                    case "power":
                        thisScore = com.cthulhu.RollDice.rollDSix(3);
                        powVal = "POW: " + Integer.toString(thisScore * 5) + "\t" + (thisScore * 5) / 2 + "\t" + thisScore;

                    case "size":
                        thisScore = com.cthulhu.RollDice.rollDSix(2);
                        sizeBase = thisScore;
                        sizeVal = "SIZ: " + Integer.toString((thisScore + 6) * 5) + "\t" + (((thisScore + 6) * 5) / 2) + "\t" + (thisScore + 6);

                    case "education":
                        thisScore = com.cthulhu.RollDice.rollDSix(2);
                        eduVal = "EDU: " + Integer.toString((thisScore + 6) * 5) + "\t" + (((thisScore + 6) * 5) / 2) + "\t" + (thisScore + 6);

                    default:
                        break;
                }
            }

            hitPoints = "Hit Points: " + Integer.toString(conBase + sizeBase);
        }

    }

}
