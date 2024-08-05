# Project Proposal

## Problem Statement
At any given time, there are both many people looking to adopt a new pet as well as many people looking to find a new home for a pet. Pets come in all ranges and sizes, from the ant colony to the Great Dane.

At the moment, the animal adoption system is broken, and very hard to navigate. For those looking to adopt an animal, it is very hard to know where to look. There is no centralized system in place, and oftentimes, the results are found are either way too far away, or else not at all what is being looked for.

Additionally, for those looking to find a new home for a pet, it is often very hard to get the word out. Without a mainstream solution to list their pets for adoption, it can be very difficult to just use word of mouth. Many owners unable to take care of their pets may feel overwhelming pressure and not know where to look for assistance. Without a helpful resource, these individuals may resort to a more drastic measure such as abandoning pets.

## Technical Solution
Create an application for posting animals available for adoption and profiles of those who wish to adopt. Make it easy to browse available pets and include necessary contact information for each pet/shelter.

> Scenario 1:
The HTD ASPCA has many animals available to adopt. They set up an authenticated account, and make profiles for each of their rescues they want to adopt out. Their pets are an instant hit. Within the week, they get over a dozen calls and emails from interested future animal adopters. Within 3 months, their shelter has adopted out all their animals for the first time in living memory.

> Scenario 2:
John Doe wants to adopt a pet but isn’t sure of what type/breed. He goes onto the website, and browses pets in his area (using an interactive map (stretch goal)). He notes down the phone numbers associated with each of the pets he has interest in, and calls them each up. Eventually, he makes a decision, and is the happy owner of a 1 year old parakeet.

## Glossary
**Pet Posting**
An animal that wishes to be adopted. Includes information about the animal including the type and location.

**Pet Type**
Includes the type of animal (Dog, Cat, Other, etc.)

**User**
Anyone looking to adopt a pet. Can look at existing pet postings

**Authenticated User**
Any person or organization that has a pet and is looking for someone to adopt a pet. They can both view pet postings, make new pet postings, and edit their existing postings.

**Location**
The place where a pet is located/available to be adopted. An authenticated user can use multiple locations but a single location can only be “owned” by one user.

## High Level Requirement
-   Browse Available Pets (ANYONE)
-   Create a Pet Posting (AUTHENTICATED USER)
-   Edit an existing Pet posting (AUTHENTICATED USER)
-   Remove an existing Pet posting (AUTHENTICATED USER)
-   CRUD for Location  (TBD Stretch Goal)
-   CRUD for users (signing up/logging in) (TBD Stretch Goal)

## User Stories / Scenarios
### Browse Available Pets (ANYONE)

-   Browse available pet postings using various filters
-   Suggested filters: 
	- type
	- location **(via interactive map (stretch goal))**
	- age
	- personality/keywords (good with pets) (TBD)

### Create a Pet Posting (AUTHENTICATED USER)
-   Precondition: User must be AUTHENTICATED
-   Add a pet available to be adopted.
-   Must include the following:
	- PET TYPE
	- Location (see below)
	- Age
	- Other keywords (stretch goal, see above)
    
### Edit an existing Pet posting (AUTHENTICATED USER)

-   Precondition: User must be AUTHENTICATED
-   Can only edit posting that they created
-   Can edit any of the fields from the pet posting
   
### Remove an existing Pet posting (AUTHENTICATED USER)

-   Precondition: User must be AUTHENTICATED
-   Can only remove postings that they created