package com.cthulhu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import static com.cthulhu.NameFetch.readJsonFromUrl;
import static com.cthulhu.RollDice.rollThemBones;

public class Main {

    public static void main(String[] args) throws JSONException, KeyManagementException, NoSuchAlgorithmException, IOException {

        // Bypass Namefake API https cert business
        CertTrust.trustMgr();

        newCharacterData.newCharacter();

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

        public enum skills {
            Accounting ("accounting"),
            Anthropology ("anthropology"),
            Appraise ("appraise"),
            Archaeology ("archaeology"),
            ArtCraft ("art/craft"),
            Charm ("charm"),
            Climb ("climb"),
            CreditRating ("credit rating"),
            ChtulhuMythos ("cthulhu mythos"),
            Disguise ("disguise"),
            Dodge ("dodge"),
            DriveAuto ("drive auto"),
            ElectricRepair ("electric repair"),
            FastTalk ("fast talk"),
            FightingBrawl ("fighting (brawl)"),
            FirearmsHandgun ("firearms (handgun)"),
            FirearmsRifleShotgun ("firearms (rifle/shotgun"),
            FirstAid ("first aid"),
            History ("history"),
            Intimidate ("intimidate"),
            Jump ("jump"),
            LanguageOther ("language (other)"),
            LanguageOwn ("language (own"),
            Law ("law"),
            LibraryUse ("library use"),
            Listen ("listen"),
            Locksmith ("locksmith"),
            MechanicalRepair ("mechanical repair"),
            Medicine ("medicine"),
            NaturalWorld ("natural world"),
            Navigate ("navigate"),
            Occult ("occult"),
            OperateHeavyMachine ("operate heavy machine"),
            Persuade ("persuade"),
            Pilot ("pilot"),
            Psychology ("psychology"),
            Psychoanalysis ("psychoanalysis"),
            Ride ("ride"),
            Science ("science"),
            SleightOfHand ("science"),
            SpotHidden ("spot hidden"),
            Stealth ("stealth"),
            Survival ("survival"),
            Swim ("swim"),
            Throw ("throw"),
            Track ("track");

            public String skill;

            skills(String name) {
                this.skill = name;
            }

            @Override
            public String toString() {
                return skill;
            }
        };

        public enum occupations {
            Antiquarian ("antiquarian"),
            Artist ("artist"),
            Athlete ("athlete"),
            Author ("author"),
            Clergy ("clergy"),
            Criminal ("criminal"),
            Dilettante ("dilettante"),
            DoctorOfMedicine ("doctor of medicine"),
            Drifter ("drifter"),
            Engineer ("engineer"),
            Entertainer ("entertainer"),
            Farmer ("farmer"),
            Journalist ("journalist"),
            Lawyer ("lawyer"),
            Librarian ("librarian"),
            MilitaryOfficer ("military officer"),
            Missionary ("missionary"),
            Musician ("musician"),
            Parapsychologist ("parapsychologist"),
            PoliceDetective ("police detective"),
            PoliceOfficer ("police officer"),
            PrivateInvestigator ("private investigator"),
            Professor ("professor"),
            Soldier ("soldier"),
            TribeMember ("tribe member"),
            Zealot ("zealot");

            public String occupation;

            occupations(String name) {
                this.occupation = name;
            }

            @Override
            public String toString() {
                return occupation;
            }
        };

    }

    public static class newCharacterData {

        // Vars for the main characteristic stats
        private static String charGender;
        private static int charAge;
        private static String charName;
        private static int strVal; // Strength
        private static int intelVal; // Intelligence
        private static int conVal; // Constitution
        private static int sizeVal; // Size
        private static int dexVal; // Dexterity
        private static int appVal; // Appearance
        private static int powVal; // Power
        private static int eduVal; // Education

        // We can configure luck right here
        private static int luckVal = rollThemBones(3, 6, false);

        // Derived attributes
        private static int conBase;
        private static int sizeBase;
        private static String hitPoints;

        public static void configureGender() {

            Scanner scanner = new Scanner(System.in);

            System.out.print("Male or female? ");

            charGender = scanner.nextLine();

        }

        public static void configureName() throws IOException {

            String nameApiUrl;

            // Prompt the user for a gender selection
            configureGender();

            // If a gender has been chosen, modify the API url
            if (charGender.length() > 0) {
                nameApiUrl = Globals.apiUrl + "/" + charGender.toLowerCase();
            } else {
                nameApiUrl = Globals.apiUrl;
            }

            // Hit that API and fetch the data
            JSONObject json = readJsonFromUrl(nameApiUrl);

            charName = (String) json.get("name");

        }

        public static void configureAge() {

            Scanner scanner = new Scanner(System.in);

            System.out.print("How old is this character? ");

            charAge = scanner.nextInt();

        }

        public static void configureStats() {

            int i;

            for (characterOptions.characteristics characteristic : characterOptions.characteristics.values()) {

                int thisScore,
                    timesFive;

                // Switch to determine stat and assign score
                switch(characteristic.toString()) {

                    case "strength":
                       thisScore = rollThemBones(3, 6, false);
                       strVal = Helpers.timesFive(thisScore);
                       break;

                    case "dexterity":
                        thisScore = rollThemBones(3, 6, false);
                        dexVal = Helpers.timesFive(thisScore);
                        break;

                    case "intelligence":
                        thisScore = rollThemBones(2, 6, true);
                        intelVal = Helpers.timesFive(thisScore);
                        break;

                    case "constitution":
                        thisScore = rollThemBones(3, 6, false);
                        conVal = Helpers.timesFive(thisScore);
                        break;

                    case "appearance":
                        thisScore = rollThemBones(3, 6, false);
                        appVal = Helpers.timesFive(thisScore);
                        break;

                    case "power":
                        thisScore = rollThemBones(3, 6, false);
                        powVal = Helpers.timesFive(thisScore);
                        break;

                    case "size":
                        thisScore = rollThemBones(2, 6, true);
                        sizeVal = Helpers.timesFive(thisScore);
                        break;

                    case "education":
                        thisScore = rollThemBones(2, 6, true);
                        eduVal = Helpers.timesFive(thisScore);
                        break;

                    default:
                        break;
                }
            }
        }

        public static void ageModifier(int age, int rolledScore) {



        }

        public static void newCharacter() throws IOException {

            configureName();
            configureAge();
            configureStats();

        }

    }

}
