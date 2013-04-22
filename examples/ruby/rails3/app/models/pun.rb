class Pun < ActiveRecord::Base
  attr_accessible :description, :rating

  validates :description,  :presence => true
end
