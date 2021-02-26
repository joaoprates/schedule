package repository

import entity.ContactEntity

class ContactRepository {
    companion object{
        private val contactList = mutableListOf<ContactEntity>()
        fun save(contact: ContactEntity){
            contactList.add(contact)
        }

        fun delete(contact: ContactEntity){
            // Search contacts
            val list = getList()

            // Find a contact by id
            var index = 0
            for (item in list.withIndex()) {
                if (item.value.name == contact.name && item.value.phone == contact.phone) {
                    index = item.index
                    break
                }
            }

            // Remove at list
            contactList.removeAt(index)
        }

        fun getList(): List<ContactEntity> {
            return contactList
        }
    }

}